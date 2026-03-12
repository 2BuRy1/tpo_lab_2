package unit.log;

import bilyardvmetro.tpolab2.log.Ln;
import bilyardvmetro.tpolab2.log.Log;
import bilyardvmetro.tpolab2.func.MathFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.BdAsserts.*;

public class LogTest {

    private final MathFunction ln = new Ln();
    private final BigDecimal eps = new BigDecimal("1E-6");
    private final BigDecimal tol = new BigDecimal("1E-3");

    private final MathFunction log2 = new Log(ln, new BigDecimal("2"));
    private final MathFunction log3 = new Log(ln, new BigDecimal("3"));
    private final MathFunction log10 = new Log(ln, new BigDecimal("10"));

    @ParameterizedTest
    @ValueSource(doubles = { 0.1, 0.2, 0.5, 2.0, 3.0, 10.0 })
    void log2_matches_math(double xd) {
        BigDecimal actual = log2.calc(bd(xd), eps);
        BigDecimal expected = bd(Math.log(xd) / Math.log(2.0));
        assertClose(expected, actual, tol);
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.1, 0.2, 0.5, 2.0, 3.0, 10.0 })
    void log3_matches_math(double xd) {
        BigDecimal actual = log3.calc(bd(xd), eps);
        BigDecimal expected = bd(Math.log(xd) / Math.log(3.0));
        assertClose(expected, actual, tol);
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.1, 0.2, 0.5, 2.0, 3.0, 10.0 })
    void log10_matches_math(double xd) {
        BigDecimal actual = log10.calc(bd(xd), eps);
        BigDecimal expected = bd(Math.log10(xd));
        assertClose(expected, actual, tol);
    }

    @Test
    void log_throws_for_non_positive() {
        assertThrows(ArithmeticException.class, () -> log2.calc(BigDecimal.ZERO, eps));
        assertThrows(ArithmeticException.class, () -> log2.calc(new BigDecimal("-1"), eps));

        assertThrows(ArithmeticException.class, () -> log3.calc(BigDecimal.ZERO, eps));
        assertThrows(ArithmeticException.class, () -> log3.calc(new BigDecimal("-1"), eps));

        assertThrows(ArithmeticException.class, () -> log10.calc(BigDecimal.ZERO, eps));
        assertThrows(ArithmeticException.class, () -> log10.calc(new BigDecimal("-1"), eps));
    }
}
