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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FuncSystemNegativeTest {

    @Test
    void system_returns_zero_when_x_equals_one() {

        MathFunction sin = new Sin();
        MathFunction cos = new Cos(sin);
        MathFunction tan = new Tan(sin, cos);
        MathFunction cot = new Cot(sin, cos);

        MathFunction ln = new Ln();
        MathFunction log2 = new Log(ln, new BigDecimal("2"));
        MathFunction log3 = new Log(ln, new BigDecimal("3"));
        MathFunction log10 = new Log(ln, new BigDecimal("10"));

        MathFunction system = new FuncSystem(
                sin, cos, tan, cot, ln,
                log2, log3, log10
        );

        BigDecimal value = assertDoesNotThrow(
                () -> system.calc(BigDecimal.ONE, new BigDecimal("1E-6"))
        );
        assertTrue(value.compareTo(BigDecimal.ZERO) == 0, "Expected numeric zero, got " + value);
    }

    @Test
    void system_throws_when_x_equals_zero() {

        MathFunction sin = new Sin();
        MathFunction cos = new Cos(sin);
        MathFunction tan = new Tan(sin, cos);
        MathFunction cot = new Cot(sin, cos);

        MathFunction ln = new Ln();
        MathFunction log2 = new Log(ln, new BigDecimal("2"));
        MathFunction log3 = new Log(ln, new BigDecimal("3"));
        MathFunction log10 = new Log(ln, new BigDecimal("10"));

        MathFunction system = new FuncSystem(
                sin, cos, tan, cot, ln,
                log2, log3, log10
        );

        assertThrows(ArithmeticException.class,
                () -> system.calc(BigDecimal.ZERO, new BigDecimal("1E-6")));
    }

    @Test
    void system_throws_when_x_equals_minus_pi() {

        MathFunction sin = new Sin();
        MathFunction cos = new Cos(sin);
        MathFunction tan = new Tan(sin, cos);
        MathFunction cot = new Cot(sin, cos);

        MathFunction ln = new Ln();
        MathFunction log2 = new Log(ln, new BigDecimal("2"));
        MathFunction log3 = new Log(ln, new BigDecimal("3"));
        MathFunction log10 = new Log(ln, new BigDecimal("10"));

        MathFunction system = new FuncSystem(
                sin, cos, tan, cot, ln,
                log2, log3, log10
        );

        assertThrows(ArithmeticException.class,
                () -> system.calc(new BigDecimal("-3.141592653589793"), new BigDecimal("1E-6")));
    }
}
