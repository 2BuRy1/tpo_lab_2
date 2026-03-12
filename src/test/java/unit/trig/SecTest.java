package unit.trig;

import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.trig.Cos;
import brizgy.tpolab2.trig.Sec;
import brizgy.tpolab2.trig.Sin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.BdAsserts.*;

public class SecTest {

    private final MathFunction sin = new Sin();
    private final MathFunction cos = new Cos(sin);
    private final MathFunction sec = new Sec(cos);

    private final BigDecimal eps = new BigDecimal("1E-6");
    private final BigDecimal tol = new BigDecimal("1E-2");

    @ParameterizedTest
    @ValueSource(doubles = { -2.0, -1.0, -0.7, -0.5, -0.2, 0.2, 0.5, 0.7, 1.0, 2.0 })
    void sec_matches_math(double xd) {
        if (Math.abs(Math.cos(xd)) < 1e-6) return;

        BigDecimal x = bd(xd);
        BigDecimal actual = sec.calc(x, eps);
        BigDecimal expected = bd(1.0 / Math.cos(xd));
        assertClose(expected, actual, tol);
    }

    @Test
    void sec_throws_when_cos_is_zero() {
        BigDecimal x = BigDecimal.valueOf(Math.PI / 2); // cos(x) ≈ 0

        assertThrows(ArithmeticException.class, () -> sec.calc(x, eps));
    }
}
