package unit.trig;

import bilyardvmetro.tpolab2.trig.*;
import bilyardvmetro.tpolab2.func.MathFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.BdAsserts.*;

public class TanTest {

    private final MathFunction sin = new Sin();
    private final MathFunction cos = new Cos(sin);
    private final MathFunction tan = new Tan(sin, cos);

    private final BigDecimal eps = new BigDecimal("1E-6");
    private final BigDecimal tol = new BigDecimal("1E-3");

    @ParameterizedTest
    @ValueSource(doubles = { -1.0, -0.7, -0.5, -0.2, 0.2, 0.5, 0.7, 1.0 })
    void tan_matches_math(double xd) {
        BigDecimal x = bd(xd);
        BigDecimal actual = tan.calc(x, eps);
        BigDecimal expected = bd(Math.tan(xd));
        assertClose(expected, actual, tol);
    }

    @Test
    void tan_throws_when_cos_is_zero() {
        BigDecimal x = BigDecimal.valueOf(Math.PI / 2); // cos(x) ≈ 0

        assertThrows(ArithmeticException.class, () -> tan.calc(x, eps));
    }
}
