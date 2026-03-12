package bilyardvmetro.tpolab2.trig;

import bilyardvmetro.tpolab2.func.MathFunction;
import bilyardvmetro.tpolab2.util.MathConfig;

import java.math.BigDecimal;

public class Sin implements MathFunction {
    @Override
    public BigDecimal calc(BigDecimal x, BigDecimal eps) {
        var result = BigDecimal.ZERO;
        var term = x;
        int n = 1;

        while (term.abs().compareTo(eps) > 0) {
            result = result.add(term, MathConfig.MC);

            // итеративная формула
            // t_n+1 = t_n * (- (x*2 / 2n*(2n + 1)))
            var numerator = term.multiply(x.pow(2), MathConfig.MC).negate();
            var denominator = new BigDecimal((2 * n) * (2 * n + 1));

            term = numerator.divide(denominator, MathConfig.MC);

            n++;
        }

        return result;
    }
}
