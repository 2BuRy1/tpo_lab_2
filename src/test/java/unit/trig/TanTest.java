package unit.trig;

import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.trig.Cos;
import brizgy.tpolab2.trig.Sin;
import brizgy.tpolab2.trig.Tan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.BdAsserts.*;

public class TanTest {

    private final MathFunction sin = new Sin();
    private final MathFunction cos = new Cos(sin);
    private final MathFunction tan = new Tan(sin, cos);

    private final BigDecimal eps = new BigDecimal("1E-6");
    private final BigDecimal tol = new BigDecimal("1E-3");

    @ParameterizedTest
    @CsvSource({
            "-1.0, -1.5574077246549020821",
            "-0.5, -0.54630248984379049497",
            "-0.2, -0.20271003550867248081",
            "0.2, 0.20271003550867248584",
            "0.5, 0.54630248984379053151",
            "1.0, 1.5574077246549022305"
    })
    void tan_matches_reference(String xs, String expectedS) {
        BigDecimal x = new BigDecimal(xs);
        BigDecimal actual = tan.calc(x, eps);
        BigDecimal expected = new BigDecimal(expectedS);
        assertClose(expected, actual, tol);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "0.5235987755982989, 0.57735026918962576451",
            "0.7853981633974483, 1",
            "1.0471975511965977, 1.7320508075688772935",
            "3.141592653589793, 0"
    })
    void tan_matches_reference_key_points(String xs, String expectedS) {
        BigDecimal x = new BigDecimal(xs);
        BigDecimal actual = tan.calc(x, eps);
        BigDecimal expected = new BigDecimal(expectedS);
        assertClose(expected, actual, tol);
    }

    @Test
    void tan_throws_when_cos_is_zero() {
        BigDecimal x = BigDecimal.valueOf(Math.PI / 2);

        assertThrows(ArithmeticException.class, () -> tan.calc(x, eps));
    }

    @ParameterizedTest
    @CsvSource({
            "2.0",
            "2.8",
            "3.4",
            "4.0"
    })
    void tan_matches_reference_in_second_and_third_quadrants(String xs) {
        BigDecimal x = new BigDecimal(xs);
        BigDecimal actual = tan.calc(x, eps);
        BigDecimal expected = BigDecimal.valueOf(Math.tan(x.doubleValue()));
        assertClose(expected, actual, new BigDecimal("1E-2"));
    }

    @Test
    void tan_changes_sign_around_pi_over_two() {
        BigDecimal left = tan.calc(new BigDecimal("1.5706963267948966"), eps);
        BigDecimal right = tan.calc(new BigDecimal("1.5708963267948966"), eps);

        assertTrue(left.compareTo(BigDecimal.ZERO) > 0);
        assertTrue(right.compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    void tan_changes_sign_around_pi() {
        BigDecimal left = tan.calc(new BigDecimal("3.131592653589793"), eps);
        BigDecimal right = tan.calc(new BigDecimal("3.151592653589793"), eps);

        assertTrue(left.compareTo(BigDecimal.ZERO) < 0);
        assertTrue(right.compareTo(BigDecimal.ZERO) > 0);
    }
}
