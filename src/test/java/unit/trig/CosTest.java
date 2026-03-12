package unit.trig;

import brizgy.tpolab2.trig.Cos;
import brizgy.tpolab2.trig.Sin;
import brizgy.tpolab2.func.MathFunction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static util.BdAsserts.*;

public class CosTest {

    private final MathFunction sin = new Sin();
    private final MathFunction cos = new Cos(sin);

    private final BigDecimal eps = new BigDecimal("1E-6");
    private final BigDecimal tol = new BigDecimal("1E-4");

    @ParameterizedTest
    @ValueSource(doubles = { -2.0, -1.0, -0.5, -0.2, 0.0, 0.2, 0.5, 1.0, 2.0 })
    void cos_matches_math(double xd) {
        BigDecimal x = bd(xd);
        BigDecimal actual = cos.calc(x, eps);
        BigDecimal expected = bd(Math.cos(xd));
        assertClose(expected, actual, tol);
    }
}