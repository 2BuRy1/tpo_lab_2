package unit.trig;

import brizgy.tpolab2.trig.Sin;
import brizgy.tpolab2.func.MathFunction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static util.BdAsserts.assertClose;

public class SinTest {

    private final MathFunction sin = new Sin();
    private final BigDecimal eps = new BigDecimal("1E-6");
    private final BigDecimal tol = new BigDecimal("1E-4");

    @ParameterizedTest
    @CsvSource({
            "-2.0, -0.90929742682568169542",
            "-1.0, -0.84147098480789650666",
            "-0.5, -0.47942553860420300026",
            "-0.2, -0.19866933079506121546",
            "0.0, 0",
            "0.2, 0.19866933079506121546",
            "0.5, 0.47942553860420300026",
            "2.0, 0.90929742682568169542"
    })
    void sin_matches_reference(String xs, String expectedS) {
        BigDecimal x = new BigDecimal(xs);
        BigDecimal actual = sin.calc(x, eps);
        BigDecimal expected = new BigDecimal(expectedS);
        assertClose(expected, actual, tol);
    }
}
