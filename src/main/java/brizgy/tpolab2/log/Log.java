package brizgy.tpolab2.log;

import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.util.MathConfig;

import java.math.BigDecimal;

import static java.lang.String.format;

public class Log implements MathFunction {
    private final MathFunction ln;
    private final BigDecimal base;

    public Log(MathFunction ln, BigDecimal base) {
        this.ln = ln;
        this.base = base;
    }

    @Override
    public BigDecimal calc(BigDecimal x, BigDecimal eps) {
        if (x.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ArithmeticException(format("Логарифм с основанием %s не имеет значения при x = %s", base, x));
        }

        return ln.calc(x, eps).divide(ln.calc(base, eps), MathConfig.MC);
    }
}
