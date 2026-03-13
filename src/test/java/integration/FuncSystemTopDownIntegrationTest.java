package integration;

import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.log.Ln;
import brizgy.tpolab2.log.Log;
import brizgy.tpolab2.stub.CsvTableStub;
import brizgy.tpolab2.system.FuncSystem;
import brizgy.tpolab2.trig.Cos;
import brizgy.tpolab2.trig.Cot;
import brizgy.tpolab2.trig.Sin;
import brizgy.tpolab2.trig.Tan;
import brizgy.tpolab2.util.CsvGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Map;

import static util.BdAsserts.assertClose;

class FuncSystemTopDownIntegrationTest {

    private static final BigDecimal EPS = new BigDecimal("1E-20");
    private static final BigDecimal TOL = new BigDecimal("1E-9");

    private static final String[] NEGATIVE_BRANCH_POINTS = {
            "-0.1", "-0.2", "-0.5", "-1.0", "-2.0"
    };

    private static final String[] POSITIVE_BRANCH_POINTS = {
            "0.1", "0.2", "0.5", "2.0", "3.0", "10.0"
    };

    private static final Map<String, BigDecimal> SYSTEM_EXPECTED = Map.ofEntries(
            Map.entry("-0.1", new BigDecimal("-69709761.114534263957")),
            Map.entry("-0.2", new BigDecimal("-187139.91403462926117")),
            Map.entry("-0.5", new BigDecimal("-336.90508631229114607")),
            Map.entry("-1.0", new BigDecimal("-120.83563643400441482")),
            Map.entry("-2.0", new BigDecimal("81.963032723991092770")),
            Map.entry("0.1", new BigDecimal("123.65627125175415034")),
            Map.entry("0.2", new BigDecimal("20.630456624518918905")),
            Map.entry("0.5", new BigDecimal("0.30567858077465062056")),
            Map.entry("2.0", new BigDecimal("-0.30567858077465062056")),
            Map.entry("3.0", new BigDecimal("-3.0574542409161089957")),
            Map.entry("10.0", new BigDecimal("-123.65627125175415034"))
    );

    @BeforeAll
    static void refreshStubTablesFromRealFunctions() throws Exception {
        CsvGenerator.generateDefaultTestResources();
    }

    @Test
    void trig_stage_0_all_stubs() throws Exception {
        Modules m = baseModules();
        assertNegativeBranchMatches(m.sinStub, m.cosStub, m.tanStub, m.cotStub, m.lnStub, m.log2Stub, m.log3Stub, m.log10Stub);
    }

    @Test
    void trig_stage_1_real_sin_rest_stubs() throws Exception {
        Modules m = baseModules();
        MathFunction sinReal = new Sin();
        assertNegativeBranchMatches(sinReal, m.cosStub, m.tanStub, m.cotStub, m.lnStub, m.log2Stub, m.log3Stub, m.log10Stub);
    }

    @Test
    void trig_stage_2_real_sin_cos_rest_stubs() throws Exception {
        Modules m = baseModules();
        MathFunction sinReal = new Sin();
        MathFunction cosReal = new Cos(sinReal);
        assertNegativeBranchMatches(sinReal, cosReal, m.tanStub, m.cotStub, m.lnStub, m.log2Stub, m.log3Stub, m.log10Stub);
    }

    @Test
    void trig_stage_3_real_sin_cos_tan_rest_stubs() throws Exception {
        Modules m = baseModules();
        MathFunction sinReal = new Sin();
        MathFunction cosReal = new Cos(sinReal);
        MathFunction tanReal = new Tan(sinReal, cosReal);
        assertNegativeBranchMatches(sinReal, cosReal, tanReal, m.cotStub, m.lnStub, m.log2Stub, m.log3Stub, m.log10Stub);
    }

    @Test
    void trig_stage_4_real_trig_logs_stubs() throws Exception {
        Modules m = baseModules();
        MathFunction sinReal = new Sin();
        MathFunction cosReal = new Cos(sinReal);
        MathFunction tanReal = new Tan(sinReal, cosReal);
        MathFunction cotReal = new Cot(sinReal, cosReal);
        assertNegativeBranchMatches(sinReal, cosReal, tanReal, cotReal, m.lnStub, m.log2Stub, m.log3Stub, m.log10Stub);
    }

    @Test
    void log_stage_0_all_stubs() throws Exception {
        Modules m = baseModules();
        assertPositiveBranchMatches(m.sinStub, m.cosStub, m.tanStub, m.cotStub, m.lnStub, m.log2Stub, m.log3Stub, m.log10Stub);
    }

    @Test
    void log_stage_1_real_ln_rest_stubs() throws Exception {
        Modules m = baseModules();
        MathFunction lnReal = new Ln();
        assertPositiveBranchMatches(m.sinStub, m.cosStub, m.tanStub, m.cotStub, lnReal, m.log2Stub, m.log3Stub, m.log10Stub);
    }

    @Test
    void log_stage_2_real_ln_log2_rest_stubs() throws Exception {
        Modules m = baseModules();
        MathFunction lnReal = new Ln();
        MathFunction log2Real = new Log(lnReal, new BigDecimal("2"));
        assertPositiveBranchMatches(m.sinStub, m.cosStub, m.tanStub, m.cotStub, lnReal, log2Real, m.log3Stub, m.log10Stub);
    }

    @Test
    void log_stage_3_real_ln_log2_log3_rest_stubs() throws Exception {
        Modules m = baseModules();
        MathFunction lnReal = new Ln();
        MathFunction log2Real = new Log(lnReal, new BigDecimal("2"));
        MathFunction log3Real = new Log(lnReal, new BigDecimal("3"));
        assertPositiveBranchMatches(m.sinStub, m.cosStub, m.tanStub, m.cotStub, lnReal, log2Real, log3Real, m.log10Stub);
    }

    @Test
    void log_stage_4_real_logs_trig_stubs() throws Exception {
        Modules m = baseModules();
        MathFunction lnReal = new Ln();
        MathFunction log2Real = new Log(lnReal, new BigDecimal("2"));
        MathFunction log3Real = new Log(lnReal, new BigDecimal("3"));
        MathFunction log10Real = new Log(lnReal, new BigDecimal("10"));
        assertPositiveBranchMatches(m.sinStub, m.cosStub, m.tanStub, m.cotStub, lnReal, log2Real, log3Real, log10Real);
    }

    private static MathFunction stub(String fileName) throws Exception {
        return new CsvTableStub(Path.of("src/test/resources/" + fileName));
    }

    private static Modules baseModules() throws Exception {
        return new Modules(
                stub("sin.csv"),
                stub("cos.csv"),
                stub("tan.csv"),
                stub("cot.csv"),
                stub("ln.csv"),
                stub("log2.csv"),
                stub("log3.csv"),
                stub("log10.csv")
        );
    }

    private static void assertNegativeBranchMatches(
            MathFunction sin,
            MathFunction cos,
            MathFunction tan,
            MathFunction cot,
            MathFunction ln,
            MathFunction log2,
            MathFunction log3,
            MathFunction log10
    ) {
        assertSystemMatches(
                new FuncSystem(sin, cos, tan, cot, ln, log2, log3, log10),
                NEGATIVE_BRANCH_POINTS
        );
    }

    private static void assertPositiveBranchMatches(
            MathFunction sin,
            MathFunction cos,
            MathFunction tan,
            MathFunction cot,
            MathFunction ln,
            MathFunction log2,
            MathFunction log3,
            MathFunction log10
    ) {
        assertSystemMatches(
                new FuncSystem(sin, cos, tan, cot, ln, log2, log3, log10),
                POSITIVE_BRANCH_POINTS
        );
    }

    private static void assertSystemMatches(MathFunction system, String[] xValues) {
        for (String xText : xValues) {
            BigDecimal x = new BigDecimal(xText);
            BigDecimal expected = expectedValue(xText);
            BigDecimal actual = system.calc(x, EPS);
            assertClose(expected, actual, TOL);
        }
    }

    private static BigDecimal expectedValue(String x) {
        BigDecimal value = SYSTEM_EXPECTED.get(x);
        if (value == null) {
            throw new IllegalArgumentException("No expected value for x=" + x);
        }
        return value;
    }

    private record Modules(
            MathFunction sinStub,
            MathFunction cosStub,
            MathFunction tanStub,
            MathFunction cotStub,
            MathFunction lnStub,
            MathFunction log2Stub,
            MathFunction log3Stub,
            MathFunction log10Stub
    ) {}
}
