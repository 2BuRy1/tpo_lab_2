package brizgy.tpolab2.trig;

import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.util.MathConfig;

import java.math.BigDecimal;

import static java.lang.String.format;

public class Tan implements MathFunction{
    private final MathFunction sin;
    private final MathFunction cos;

    public Tan(MathFunction sin, MathFunction cos) {
        this.sin = sin;
        this.cos = cos;
    }

    @Override
    public BigDecimal calc(BigDecimal x, BigDecimal eps) {
        var c = cos.calc(x, eps);

        if (c.abs().compareTo(eps) < 0) {
            throw new ArithmeticException(format("У тангенса нет значения при x = %s", x));
        }

        return sin.calc(x, eps).divide(c, MathConfig.MC);
    }
}
