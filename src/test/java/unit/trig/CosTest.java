package unit.trig;

import brizgy.tpolab2.trig.Cos;
import brizgy.tpolab2.trig.Sin;
import brizgy.tpolab2.func.MathFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static util.BdAsserts.*;

public class CosTest {

    private static final BigDecimal TWO_PI = new BigDecimal("6.2831853071795864769");

    private final MathFunction sin = new Sin();
    private final MathFunction cos = new Cos(sin);

    private final BigDecimal eps = new BigDecimal("1E-6");
    private final BigDecimal tol = new BigDecimal("1E-4");

    @ParameterizedTest
    @CsvSource({
            "-2.0, -0.41614683654714233135",
            "-1.0, 0.54030230586813976890",
            "-0.5, 0.87758256189037274547",
            "-0.2, 0.98006657784124164329",
            "0.0, 1",
            "0.2, 0.98006657784124161897",
            "0.5, 0.87758256189037268676",
            "2.0, -0.41614683654714244268"
    })
    void cos_matches_reference(String xs, String expectedS) {
        BigDecimal x = new BigDecimal(xs);
        BigDecimal actual = cos.calc(x, eps);
        BigDecimal expected = new BigDecimal(expectedS);
        assertClose(expected, actual, tol);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "0.5235987755982989, 0.86602540378443864676",
            "0.7853981633974483, 0.70710678118654752440",
            "1.0471975511965977, 0.5",
            "3.141592653589793, -1"
    })
    void cos_matches_reference_key_points(String xs, String expectedS) {
        BigDecimal x = new BigDecimal(xs);
        BigDecimal actual = cos.calc(x, eps);
        BigDecimal expected = new BigDecimal(expectedS);
        assertClose(expected, actual, tol);
    }

    @Test
    void cos_is_periodic_with_2pi() {
        BigDecimal x = new BigDecimal("0.37");
        BigDecimal base = cos.calc(x, eps);
        BigDecimal shifted = cos.calc(x.add(TWO_PI), eps);
        assertClose(base, shifted, new BigDecimal("1E-4"));
    }
}
