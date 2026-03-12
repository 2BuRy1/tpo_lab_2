package brizgy.tpolab2.trig;

import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.util.MathConfig;

import java.math.BigDecimal;

public class Sin implements MathFunction {
    @Override
    public BigDecimal calc(BigDecimal x, BigDecimal eps) {
        var result = BigDecimal.ZERO;
        var term = x;
        int n = 1;

        while (term.abs().compareTo(eps) > 0) {
            result = result.add(term, MathConfig.MC);

            var numerator = term.multiply(x.pow(2), MathConfig.MC).negate();
            var denominator = new BigDecimal((2 * n) * (2 * n + 1));

            term = numerator.divide(denominator, MathConfig.MC);

            n++;
        }

        return result;
    }
}
