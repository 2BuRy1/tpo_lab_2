package unit.log;

import bilyardvmetro.tpolab2.log.Ln;
import bilyardvmetro.tpolab2.func.MathFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static util.BdAsserts.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LnTest {

    private final MathFunction ln = new Ln();
    private final BigDecimal eps = new BigDecimal("1E-6");
    private final BigDecimal tol = new BigDecimal("1E-3");

    @ParameterizedTest
    @ValueSource(doubles = { 0.1, 0.2, 0.5, 1.0, 2.0, 3.0, 10.0 })
    void ln_matches_math(double xd) {
        BigDecimal x = bd(xd);
        BigDecimal actual = ln.calc(x, eps);
        BigDecimal expected = bd(Math.log(xd));
        assertClose(expected, actual, tol);
    }

    @Test
    void ln_throws_for_non_positive() {
        assertThrows(ArithmeticException.class, () -> ln.calc(BigDecimal.ZERO, eps));
        assertThrows(ArithmeticException.class, () -> ln.calc(new BigDecimal("-1"), eps));
    }
}
