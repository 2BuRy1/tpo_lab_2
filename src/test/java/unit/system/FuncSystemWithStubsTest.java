package unit.system;

import bilyardvmetro.tpolab2.func.MathFunction;
import bilyardvmetro.tpolab2.stub.CsvTableStub;
import bilyardvmetro.tpolab2.system.FuncSystem;
import bilyardvmetro.tpolab2.trig.*;
import bilyardvmetro.tpolab2.log.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Path;

public class FuncSystemWithStubsTest {

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

        var expected = new CsvTableStub(Path.of("src/test/resources/system_expected.csv"));

        BigDecimal eps = new BigDecimal("1E-20"); // не влияет при стабе

        for (String xs : new String[]{
                "-0.1","-0.2","-0.5","-1.0","-2.0",
                "0.1","0.2","0.5","2.0","3.0","10.0"
        }) {
            BigDecimal x = new BigDecimal(xs);

            BigDecimal actual = system.calc(x, eps);
            BigDecimal exp = expected.calc(x, eps);

            assertClose(exp, actual, new BigDecimal("1E-9"));
        }
    }

    @Test
    void system_real_trig_stub_logs() throws Exception {
        BigDecimal eps = new BigDecimal("1E-20");

        // REAL trig
        MathFunction sin = new Sin();
        MathFunction cos = new Cos(sin);
        MathFunction tan = new Tan(sin, cos);
        MathFunction cot = new Cot(sin, cos);

        // STUB logs (их не должно быть в этой ветке)
        MathFunction ln = new CsvTableStub(Path.of("src/test/resources/ln.csv"));
        MathFunction log2 = new CsvTableStub(Path.of("src/test/resources/log2.csv"));
        MathFunction log3 = new CsvTableStub(Path.of("src/test/resources/log3.csv"));
        MathFunction log10 = new CsvTableStub(Path.of("src/test/resources/log10.csv"));

        MathFunction system = new FuncSystem(sin, cos, tan, cot, ln, log2, log3, log10);
        var expected = new CsvTableStub(Path.of("src/test/resources/system_expected.csv"));

        for (String xs : new String[]{"-0.1","-0.2","-0.5","-1.0","-2.0"}) {
            BigDecimal x = new BigDecimal(xs);

            BigDecimal actual = system.calc(x, eps);
            BigDecimal exp = expected.calc(x, eps);
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
        var expected = new CsvTableStub(Path.of("src/test/resources/system_expected.csv"));

        for (String xs : new String[]{"0.1","0.2","0.5","2.0","3.0","10.0"}) {
            BigDecimal x = new BigDecimal(xs);

            BigDecimal actual = system.calc(x, eps);
            BigDecimal exp = expected.calc(x, eps);
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
        var expected = new CsvTableStub(Path.of("src/test/resources/system_expected.csv"));

        for (String xs : new String[]{
                "-0.1","-0.2","-0.5","-1.0","-2.0",
                "0.1","0.2","0.5","2.0","3.0","10.0"
        }) {
            BigDecimal x = new BigDecimal(xs);

            BigDecimal actual = system.calc(x, eps);
            BigDecimal exp = expected.calc(x, eps);

            assertClose(exp, actual, new BigDecimal("1E-9"));
        }
    }


    private static void assertClose(BigDecimal expected, BigDecimal actual, BigDecimal tol) {
        BigDecimal diff = expected.subtract(actual).abs();
        if (diff.compareTo(tol) > 0) {
            throw new AssertionError("Expected " + expected + ", actual " + actual + ", diff " + diff);
        }
    }
}
