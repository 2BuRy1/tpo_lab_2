package unit.trig;

import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.trig.Cos;
import brizgy.tpolab2.trig.Cot;
import brizgy.tpolab2.trig.Sin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.BdAsserts.*;

public class CotTest {

    private final MathFunction sin = new Sin();
    private final MathFunction cos = new Cos(sin);
    private final MathFunction cot = new Cot(sin, cos);

    private final BigDecimal eps = new BigDecimal("1E-6");
    private final BigDecimal tol = new BigDecimal("1E-2");

    @ParameterizedTest
    @CsvSource({
            "-2.0, 0.45765755436028570254",
            "-1.0, -0.64209261593433076420",
            "-0.5, -1.8304877217124519805",
            "-0.2, -4.9331548755868937186",
            "0.2, 4.9331548755868935962",
            "0.5, 1.8304877217124518581",
            "1.0, 0.64209261593433063756",
            "2.0, -0.45765755436028582497"
    })
    void cot_matches_reference(String xs, String expectedS) {
        BigDecimal x = new BigDecimal(xs);
        BigDecimal actual = cot.calc(x, eps);
        BigDecimal expected = new BigDecimal(expectedS);
        assertClose(expected, actual, tol);
    }

    @ParameterizedTest
    @CsvSource({
            "0.5235987755982989, 1.7320508075688772935",
            "0.7853981633974483, 1",
            "1.0471975511965977, 0.57735026918962576451",
            "1.5707963267948966, 0"
    })
    void cot_matches_reference_key_points(String xs, String expectedS) {
        BigDecimal x = new BigDecimal(xs);
        BigDecimal actual = cot.calc(x, eps);
        BigDecimal expected = new BigDecimal(expectedS);
        assertClose(expected, actual, tol);
    }

    @Test
    void cot_throws_when_sin_is_zero() {
        assertThrows(ArithmeticException.class, () -> cot.calc(BigDecimal.ZERO, eps));
    }
}
