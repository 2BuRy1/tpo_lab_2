package unit.log;

import brizgy.tpolab2.log.Ln;
import brizgy.tpolab2.func.MathFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static util.BdAsserts.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LnTest {

    private final MathFunction ln = new Ln();
    private final BigDecimal eps = new BigDecimal("1E-6");
    private final BigDecimal tol = new BigDecimal("1E-3");

    @ParameterizedTest
    @CsvSource({
            "0.1, -2.3025850929940456836",
            "0.2, -1.6094379124341003746",
            "0.5, -0.69314718055994530940",
            "1.0, 0",
            "2.0, 0.69314718055994530940",
            "3.0, 1.0986122886681096914",
            "10.0, 2.3025850929940456836"
    })
    void ln_matches_reference(String xs, String expectedS) {
        BigDecimal x = new BigDecimal(xs);
        BigDecimal actual = ln.calc(x, eps);
        BigDecimal expected = new BigDecimal(expectedS);
        assertClose(expected, actual, tol);
    }

    @Test
    void ln_throws_for_non_positive() {
        assertThrows(ArithmeticException.class, () -> ln.calc(BigDecimal.ZERO, eps));
        assertThrows(ArithmeticException.class, () -> ln.calc(new BigDecimal("-1"), eps));
    }
}
