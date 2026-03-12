package unit.system;

import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.log.Ln;
import brizgy.tpolab2.log.Log;
import brizgy.tpolab2.stub.CsvTableStub;
import brizgy.tpolab2.system.FuncSystem;
import brizgy.tpolab2.trig.Cos;
import brizgy.tpolab2.trig.Cot;
import brizgy.tpolab2.trig.Sin;
import brizgy.tpolab2.trig.Tan;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Map;

public class FuncSystemWithStubsTest {

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

    @Test
    void system_matches_expected_using_stubs() throws Exception {
        var sin = new CsvTableStub(Path.of("src/test/resources/sin.csv"));
        var cos = new CsvTableStub(Path.of("src/test/resources/cos.csv"));
        var tan = new CsvTableStub(Path.of("src/test/resources/tan.csv"));
        var cot = new CsvTableStub(Path.of("src/test/resources/cot.csv"));

        var ln = new CsvTableStub(Path.of("src/test/resources/ln.csv"));
        var log2 = new CsvTableStub(Path.of("src/test/resources/log2.csv"));
        var log3 = new CsvTableStub(Path.of("src/test/resources/log3.csv"));
        var log10 = new CsvTableStub(Path.of("src/test/resources/log10.csv"));

        var system = new FuncSystem(sin, cos, tan, cot, ln, log2, log3, log10);

        BigDecimal eps = new BigDecimal("1E-20");

        for (String xs : new String[]{
                "-0.1","-0.2","-0.5","-1.0","-2.0",
                "0.1","0.2","0.5","2.0","3.0","10.0"
        }) {
            BigDecimal x = new BigDecimal(xs);

            BigDecimal actual = system.calc(x, eps);
            BigDecimal exp = expectedValue(xs);

            assertClose(exp, actual, new BigDecimal("1E-9"));
        }
    }

    @Test
    void system_real_trig_stub_logs() throws Exception {
        BigDecimal eps = new BigDecimal("1E-20");

        MathFunction sin = new Sin();
        MathFunction cos = new Cos(sin);
        MathFunction tan = new Tan(sin, cos);
        MathFunction cot = new Cot(sin, cos);

        MathFunction ln = new CsvTableStub(Path.of("src/test/resources/ln.csv"));
        MathFunction log2 = new CsvTableStub(Path.of("src/test/resources/log2.csv"));
        MathFunction log3 = new CsvTableStub(Path.of("src/test/resources/log3.csv"));
        MathFunction log10 = new CsvTableStub(Path.of("src/test/resources/log10.csv"));

        MathFunction system = new FuncSystem(sin, cos, tan, cot, ln, log2, log3, log10);

        for (String xs : new String[]{"-0.1","-0.2","-0.5","-1.0","-2.0"}) {
            BigDecimal x = new BigDecimal(xs);

            BigDecimal actual = system.calc(x, eps);
            BigDecimal exp = expectedValue(xs);
            assertClose(exp, actual, new BigDecimal("1E-9"));
        }
    }

    @Test
    void system_real_logs_stub_trig() throws Exception {
        BigDecimal eps = new BigDecimal("1E-20");

        // STUB trig (не должны использоваться при x>0)
        MathFunction sin = new CsvTableStub(Path.of("src/test/resources/sin.csv"));
        MathFunction cos = new CsvTableStub(Path.of("src/test/resources/cos.csv"));
        MathFunction tan = new CsvTableStub(Path.of("src/test/resources/tan.csv"));
        MathFunction cot = new CsvTableStub(Path.of("src/test/resources/cot.csv"));

        // REAL logs
        MathFunction ln = new Ln();
        MathFunction log2 = new Log(ln, new BigDecimal("2"));
        MathFunction log3 = new Log(ln, new BigDecimal("3"));
        MathFunction log10 = new Log(ln, new BigDecimal("10"));

        MathFunction system = new FuncSystem(sin, cos, tan, cot, ln, log2, log3, log10);

        for (String xs : new String[]{"0.1","0.2","0.5","2.0","3.0","10.0"}) {
            BigDecimal x = new BigDecimal(xs);

            BigDecimal actual = system.calc(x, eps);
            BigDecimal exp = expectedValue(xs);
            assertClose(exp, actual, new BigDecimal("1E-9"));
        }
    }

    @Test
    void system_real() throws Exception {
        BigDecimal eps = new BigDecimal("1E-20");

        // REAL trig
        MathFunction sin = new Sin();
        MathFunction cos = new Cos(sin);
        MathFunction tan = new Tan(sin, cos);
        MathFunction cot = new Cot(sin, cos);

        // REAL logs
        MathFunction ln = new Ln();
        MathFunction log2 = new Log(ln, new BigDecimal("2"));
        MathFunction log3 = new Log(ln, new BigDecimal("3"));
        MathFunction log10 = new Log(ln, new BigDecimal("10"));

        MathFunction system = new FuncSystem(sin, cos, tan, cot, ln, log2, log3, log10);

        for (String xs : new String[]{
                "-0.1","-0.2","-0.5","-1.0","-2.0",
                "0.1","0.2","0.5","2.0","3.0","10.0"
        }) {
            BigDecimal x = new BigDecimal(xs);

            BigDecimal actual = system.calc(x, eps);
            BigDecimal exp = expectedValue(xs);

            assertClose(exp, actual, new BigDecimal("1E-9"));
        }
    }

    private static BigDecimal expectedValue(String x) {
        BigDecimal value = SYSTEM_EXPECTED.get(x);
        if (value == null) {
            throw new IllegalArgumentException("No reference value for x=" + x);
        }
        return value;
    }


    private static void assertClose(BigDecimal expected, BigDecimal actual, BigDecimal tol) {
        BigDecimal diff = expected.subtract(actual).abs();
        if (diff.compareTo(tol) > 0) {
            throw new AssertionError("Expected " + expected + ", actual " + actual + ", diff " + diff);
        }
    }
}
