package unit.trig;

import brizgy.tpolab2.trig.Sin;
import brizgy.tpolab2.func.MathFunction;
import util.BdAsserts;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static util.BdAsserts.assertClose;

public class SinTest {

    private final MathFunction sin = new Sin();
    private final BigDecimal eps = new BigDecimal("1E-6");
    private final BigDecimal tol = new BigDecimal("1E-4");

    @ParameterizedTest
    @ValueSource(doubles = { -2.0, -1.0, -0.5, -0.2, 0.0, 0.2, 0.5, 1.0, 2.0 })
    void sin_matches_math(double xd) {
        BigDecimal x = BdAsserts.bd(xd);
        BigDecimal actual = sin.calc(x, eps);
        BigDecimal expected = BdAsserts.bd(Math.sin(xd));
        assertClose(expected, actual, tol);
    }
}