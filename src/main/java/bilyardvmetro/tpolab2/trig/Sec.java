package bilyardvmetro.tpolab2.trig;

import bilyardvmetro.tpolab2.func.MathFunction;
import bilyardvmetro.tpolab2.util.MathConfig;

import java.math.BigDecimal;

import static java.lang.String.format;

public class Sec implements MathFunction {

    private final MathFunction cos;

    public Sec(MathFunction cos) {
        this.cos = cos;
    }

    @Override
    public BigDecimal calc(BigDecimal x, BigDecimal eps) {
        var c = cos.calc(x, eps);

        if (c.abs().compareTo(eps) < 0) {
            throw new ArithmeticException(format("У тангенса нет значения при x = %s", x));
        }

        return BigDecimal.ONE.divide(c, MathConfig.MC);
    }
}
