package unit.trig;

import brizgy.tpolab2.trig.Cos;
import brizgy.tpolab2.trig.Sin;
import brizgy.tpolab2.func.MathFunction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static util.BdAsserts.*;

public class CosTest {

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
}
