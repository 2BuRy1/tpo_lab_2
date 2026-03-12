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

    private static final BigDecimal TWO_PI = new BigDecimal("6.2831853071795864769");

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
    void sec_matches_reference_key_points() {
        assertClose(new BigDecimal("1"), sec.calc(new BigDecimal("0"), eps), tol);
        assertClose(new BigDecimal("1.1547005383792515290"), sec.calc(new BigDecimal("0.5235987755982989"), eps), tol);
        assertClose(new BigDecimal("1.4142135623730950488"), sec.calc(new BigDecimal("0.7853981633974483"), eps), tol);
        assertClose(new BigDecimal("2"), sec.calc(new BigDecimal("1.0471975511965977"), eps), tol);
        assertClose(new BigDecimal("-1"), sec.calc(new BigDecimal("3.141592653589793"), eps), tol);
    }

    @Test
    void sec_is_periodic_with_2pi() {
        BigDecimal x = new BigDecimal("0.37");
        BigDecimal base = sec.calc(x, eps);
        BigDecimal shifted = sec.calc(x.add(TWO_PI), eps);
        assertClose(base, shifted, new BigDecimal("1E-2"));
    }

    @Test
    void sec_throws_when_cos_is_zero() {
        BigDecimal x1 = BigDecimal.valueOf(Math.PI / 2);
        BigDecimal x2 = BigDecimal.valueOf(-Math.PI / 2);

        assertThrows(ArithmeticException.class, () -> sec.calc(x1, eps));
        assertThrows(ArithmeticException.class, () -> sec.calc(x2, eps));
    }
}
