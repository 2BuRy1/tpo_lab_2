package unit.trig;

import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.trig.Cos;
import brizgy.tpolab2.trig.Sec;
import brizgy.tpolab2.trig.Sin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    @CsvSource({
            "-2.0, -2.4029979617223813111",
            "-1.0, 1.8508157176809254415",
            "-0.5, 1.1394939273245490842",
            "-0.2, 1.0203388449411926771",
            "0.2, 1.0203388449411927024",
            "0.5, 1.1394939273245491604",
            "1.0, 1.8508157176809256179",
            "2.0, -2.4029979617223806682"
    })
    void sec_matches_reference(String xs, String expectedS) {
        BigDecimal x = new BigDecimal(xs);
        BigDecimal actual = sec.calc(x, eps);
        BigDecimal expected = new BigDecimal(expectedS);
        assertClose(expected, actual, tol);
    }

    @Test
    void sec_throws_when_cos_is_zero() {
        BigDecimal x = BigDecimal.valueOf(Math.PI / 2); // cos(x) ≈ 0

        assertThrows(ArithmeticException.class, () -> sec.calc(x, eps));
    }
}
