package bilyardvmetro.tpolab2.util;

import bilyardvmetro.tpolab2.func.MathFunction;
import bilyardvmetro.tpolab2.log.Ln;
import bilyardvmetro.tpolab2.log.Log;
import bilyardvmetro.tpolab2.system.FuncSystem;
import bilyardvmetro.tpolab2.trig.*;

import java.math.BigDecimal;

public class ModuleFactory {

    public static MathFunction build(String name, BigDecimal epsForBase) {
        MathFunction sin = new Sin();
        MathFunction cos = new Cos(sin);
        MathFunction tan = new Tan(sin, cos);
        MathFunction cot = new Cot(sin, cos);
        MathFunction sec = new Sec(cos);

        MathFunction ln = new Ln();
        MathFunction log2 = new Log(ln, new BigDecimal("2"));
        MathFunction log3 = new Log(ln, new BigDecimal("3"));
        MathFunction log10 = new Log(ln, new BigDecimal("10"));

        MathFunction system = new FuncSystem(sin, cos, tan, cot, ln, log2, log3, log10);

        return switch (name.toLowerCase()) {
            case "sin" -> sin;
            case "cos" -> cos;
            case "tan" -> tan;
            case "cot" -> cot;
            case "sec" -> sec;

            case "ln" -> ln;
            case "log2" -> log2;
            case "log3" -> log3;
            case "log10" -> log10;

            case "system" -> system;
            default -> throw new IllegalArgumentException("Unknown module: " + name);
        };
    }
}
