package unit.trig;

import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.trig.Cos;
import brizgy.tpolab2.trig.Cot;
import brizgy.tpolab2.trig.Sin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
    @ValueSource(doubles = { -2.0, -1.0, -0.7, -0.5, -0.2, 0.2, 0.5, 0.7, 1.0, 2.0 })
    void cot_matches_math(double xd) {
        // избегаем сингулярностей: если sin очень близок к 0 — пропускаем
        if (Math.abs(Math.sin(xd)) < 1e-6) return;

        BigDecimal x = bd(xd);
        BigDecimal actual = cot.calc(x, eps);
        BigDecimal expected = bd(Math.cos(xd) / Math.sin(xd));
        assertClose(expected, actual, tol);
    }

    @Test
    void cot_throws_when_sin_is_zero() {
        assertThrows(ArithmeticException.class, () -> cot.calc(BigDecimal.ZERO, eps));
    }
}
