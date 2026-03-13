package unit.system;

import brizgy.tpolab2.log.Ln;
import brizgy.tpolab2.log.Log;
import brizgy.tpolab2.system.FuncSystem;
import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.trig.Cos;
import brizgy.tpolab2.trig.Cot;
import brizgy.tpolab2.trig.Sin;
import brizgy.tpolab2.trig.Tan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FuncSystemNegativeTest {

    @Test
    void system_returns_zero_when_x_equals_one() {
        MathFunction system = realSystem();

        BigDecimal value = assertDoesNotThrow(
                () -> system.calc(BigDecimal.ONE, new BigDecimal("1E-6"))
        );
        assertTrue(value.compareTo(BigDecimal.ZERO) == 0, "Expected numeric zero, got " + value);
    }

    @Test
    void system_throws_when_x_equals_zero() {
        MathFunction system = realSystem();

        assertThrows(ArithmeticException.class,
                () -> system.calc(BigDecimal.ZERO, new BigDecimal("1E-6")));
    }

    @ParameterizedTest
    @CsvSource({
            "-6.283185307179586",
            "-4.71238898038469",
            "-3.141592653589793",
            "-1.5707963267948966",
            "0"
    })
    void system_throws_on_periodic_singularities(String xs) {
        MathFunction system = realSystem();
        BigDecimal x = new BigDecimal(xs);

        assertThrows(ArithmeticException.class,
                () -> system.calc(x, new BigDecimal("1E-6")));
    }

    @ParameterizedTest
    @CsvSource({
            "-6.293185307179586,-6.273185307179586,-1,1",
            "-4.72238898038469,-4.70238898038469,1,-1",
            "-3.151592653589793,-3.131592653589793,-1,1",
            "-1.5807963267948966,-1.5607963267948966,1,-1"
    })
    void system_changes_sign_around_periodic_singularities(
            String leftXText,
            String rightXText,
            int leftSign,
            int rightSign
    ) {
        MathFunction system = realSystem();
        BigDecimal eps = new BigDecimal("1E-6");

        BigDecimal leftValue = system.calc(new BigDecimal(leftXText), eps);
        BigDecimal rightValue = system.calc(new BigDecimal(rightXText), eps);

        assertTrue(Integer.signum(leftValue.signum()) == Integer.signum(leftSign));
        assertTrue(Integer.signum(rightValue.signum()) == Integer.signum(rightSign));
    }

    private static MathFunction realSystem() {
        MathFunction sin = new Sin();
        MathFunction cos = new Cos(sin);
        MathFunction tan = new Tan(sin, cos);
        MathFunction cot = new Cot(sin, cos);

        MathFunction ln = new Ln();
        MathFunction log2 = new Log(ln, new BigDecimal("2"));
        MathFunction log3 = new Log(ln, new BigDecimal("3"));
        MathFunction log10 = new Log(ln, new BigDecimal("10"));

        return new FuncSystem(
                sin, cos, tan, cot, ln,
                log2, log3, log10
        );
    }
}
