package bilyardvmetro.tpolab2.trig;

import bilyardvmetro.tpolab2.func.MathFunction;
import bilyardvmetro.tpolab2.util.MathConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.String.format;

public class Cot implements MathFunction{
    private final MathFunction sin;
    private final MathFunction cos;

    public Cot(MathFunction sin, MathFunction cos) {
        this.sin = sin;
        this.cos = cos;
    }

    @Override
    public BigDecimal calc(BigDecimal x, BigDecimal eps) {
        var s = sin.calc(x, eps);

        if (s.abs().compareTo(eps) < 0) {
            throw new ArithmeticException(format("У котангенса нет значения при x = %s", x));
        }

        return cos.calc(x, eps).divide(s, MathConfig.MC);
    }
}
