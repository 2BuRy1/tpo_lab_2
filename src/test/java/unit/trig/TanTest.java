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

    @Test
    void tan_throws_when_cos_is_zero() {
        BigDecimal x = BigDecimal.valueOf(Math.PI / 2);

        assertThrows(ArithmeticException.class, () -> tan.calc(x, eps));
    }
}
