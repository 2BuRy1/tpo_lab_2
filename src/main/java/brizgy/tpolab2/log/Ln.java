package brizgy.tpolab2.log;

import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.util.MathConfig;

import java.math.BigDecimal;

import static java.lang.String.format;

public class Ln implements MathFunction {
    @Override
    public BigDecimal calc(BigDecimal x, BigDecimal eps) throws ArithmeticException {
        if (x.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ArithmeticException(format("Натуральный логарифм не имеет значения при x = %s", x));
        }

        if (x.compareTo(BigDecimal.ONE) == 0) {
            return BigDecimal.ZERO;
        }

        var one = BigDecimal.ONE;

        var y = x.subtract(one).divide(x.add(one), MathConfig.MC);
        var y2 = y.multiply(y, MathConfig.MC);

        var term = y;
        var result = BigDecimal.ZERO;
        int n = 1;

        while (term.abs().compareTo(eps) > 0) {
            var divisor = new BigDecimal(2 * n - 1);

            result = result.add(
                    term.divide(divisor, MathConfig.MC),
                    MathConfig.MC
            );

            term = term.multiply(y2, MathConfig.MC);
            n++;
        }

        return result.multiply(new BigDecimal(2), MathConfig.MC);
    }
}
