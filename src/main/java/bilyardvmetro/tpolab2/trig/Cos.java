package bilyardvmetro.tpolab2.trig;

import bilyardvmetro.tpolab2.func.MathFunction;
import bilyardvmetro.tpolab2.util.MathConfig;

import java.math.BigDecimal;

public class Cos implements MathFunction {

    private final MathFunction sin;

    private static final BigDecimal HALF_PI = new BigDecimal(Math.PI / 2, MathConfig.MC);

    public Cos(MathFunction sin) {
        this.sin = sin;
    }

    // cos(x) = sin(pi/2 - x)
    @Override
    public BigDecimal calc(BigDecimal x, BigDecimal eps) {
        return sin.calc(HALF_PI.subtract(x), eps);
    }
}
