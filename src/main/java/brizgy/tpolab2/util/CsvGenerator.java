package brizgy.tpolab2.util;

import brizgy.tpolab2.func.MathFunction;
import bilyardvmetro.tpolab2.log.*;
import brizgy.tpolab2.log.Ln;
import brizgy.tpolab2.log.Log;
import brizgy.tpolab2.system.FuncSystem;
import bilyardvmetro.tpolab2.trig.*;
import brizgy.tpolab2.trig.*;

import java.io.BufferedWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvGenerator {

    public static void main(String[] args) throws Exception {

        BigDecimal eps = new BigDecimal("1E-20");

        MathFunction sin = new Sin();
        MathFunction cos = new Cos(sin);
        MathFunction tan = new Tan(sin, cos);
        MathFunction cot = new Cot(sin, cos);
        MathFunction sec = new Sec(cos);

        MathFunction ln = new Ln();
        MathFunction log2 = new Log(ln, new BigDecimal("2"));
        MathFunction log3 = new Log(ln, new BigDecimal("3"));
        MathFunction log10 = new Log(ln, new BigDecimal("10"));

        MathFunction system = new FuncSystem(
                sin, cos, tan, cot, ln,
                log2, log3, log10
        );

        List<BigDecimal> xs = List.of(
                new BigDecimal("-0.1"),
                new BigDecimal("-0.2"),
                new BigDecimal("-0.5"),
                new BigDecimal("-1.0"),
                new BigDecimal("-2.0"),
                new BigDecimal("0.1"),
                new BigDecimal("0.2"),
                new BigDecimal("0.5"),
                new BigDecimal("2.0"),
                new BigDecimal("3.0"),
                new BigDecimal("10.0")
        );

        writeCsv(xs, sin, eps, "sin.csv");
        writeCsv(xs, cos, eps, "cos.csv");
        writeCsv(xs, tan, eps, "tan.csv");
        writeCsv(xs, cot, eps, "cot.csv");
        writeCsv(xs, sec, eps, "sec.csv");

        writeCsv(xs, ln, eps, "ln.csv");
        writeCsv(xs, log2, eps, "log2.csv");
        writeCsv(xs, log3, eps, "log3.csv");
        writeCsv(xs, log10, eps, "log10.csv");

        writeCsv(xs, system, eps, "system_expected.csv");
    }

    private static void writeCsv(
            List<BigDecimal> xs,
            MathFunction fn,
            BigDecimal eps,
            String name
    ) throws Exception {

        Path path = Path.of("src/test/resources/" + name);

        try (BufferedWriter w = Files.newBufferedWriter(path)) {

            w.write("x,value");
            w.newLine();

            for (BigDecimal x : xs) {

                try {
                    BigDecimal y = fn.calc(x, eps);

                    w.write(x.toPlainString());
                    w.write(",");
                    w.write(y.toPlainString());
                    w.newLine();

                } catch (Exception ignored) {
                }
            }
        }
    }
}
