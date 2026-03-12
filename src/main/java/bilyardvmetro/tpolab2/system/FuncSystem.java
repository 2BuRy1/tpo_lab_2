package bilyardvmetro.tpolab2.system;

import bilyardvmetro.tpolab2.func.MathFunction;
import bilyardvmetro.tpolab2.util.MathConfig;

import java.math.BigDecimal;

public class FuncSystem implements MathFunction {

    private final MathFunction sin;
    private final MathFunction cos;
    private final MathFunction tan;
    private final MathFunction cot;

    private final MathFunction ln;
    private final MathFunction log2;
    private final MathFunction log3;
    private final MathFunction log10;

    public FuncSystem(
            MathFunction sin,
            MathFunction cos,
            MathFunction tan,
            MathFunction cot,
            MathFunction ln,
            MathFunction log2,
            MathFunction log3,
            MathFunction log10) {

        this.sin = sin;
        this.cos = cos;
        this.tan = tan;
        this.cot = cot;
        this.ln = ln;
        this.log2 = log2;
        this.log3 = log3;
        this.log10 = log10;
    }

    @Override
    public BigDecimal calc(BigDecimal x, BigDecimal eps) {
        if (x.compareTo(BigDecimal.ZERO) <= 0) {
            var sinx = sin.calc(x, eps);
            var cosx = cos.calc(x, eps);
            var tanx = tan.calc(x, eps);
            var cotx = cot.calc(x, eps);
            var cscx = BigDecimal.ONE.divide(sinx, MathConfig.MC);

            var part1 = cscx.pow(2).add(cosx).pow(2);
            var part2 = part1.divide(cotx, MathConfig.MC);
            var part3 = part2.divide(
                    cotx.add(cotx).multiply(cotx),
                    MathConfig.MC
            );

            var part4 = cosx
                    .multiply(sinx.multiply(cscx.add(tanx), MathConfig.MC), MathConfig.MC)
                    .divide(cotx, MathConfig.MC);

            var part5 = part3.divide(part4, MathConfig.MC);
            return part5.pow(2).pow(2).multiply(cotx, MathConfig.MC);
        } else {
            var lnx = ln.calc(x, eps);
            var l2 = log2.calc(x, eps);
            var l3 = log3.calc(x, eps);
            var l10 = log10.calc(x, eps);

            return l10.subtract(l2)
                    .multiply(l2, MathConfig.MC)
                    .multiply(lnx, MathConfig.MC)
                    .multiply(l2, MathConfig.MC)
                    .multiply(l3, MathConfig.MC);
        }
    }
}
