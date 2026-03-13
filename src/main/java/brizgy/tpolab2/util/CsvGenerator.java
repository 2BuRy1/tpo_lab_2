package brizgy.tpolab2.util;

import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.log.Ln;
import brizgy.tpolab2.log.Log;
import brizgy.tpolab2.system.FuncSystem;
import brizgy.tpolab2.trig.*;

import java.io.BufferedWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvGenerator {

    private static final List<BigDecimal> DEFAULT_XS = List.of(
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

    public static void main(String[] args) throws Exception {
        generateDefaultTestResources();
    }

    public static void generateDefaultTestResources() throws Exception {
        generateForPoints(DEFAULT_XS, new BigDecimal("1E-20"), Path.of("src/test/resources"));
    }

    public static void generateForPoints(
            List<BigDecimal> xs,
            BigDecimal eps,
            Path outputDir
    ) throws Exception {

        Files.createDirectories(outputDir);

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

        writeCsv(xs, sin, eps, outputDir.resolve("sin.csv"));
        writeCsv(xs, cos, eps, outputDir.resolve("cos.csv"));
        writeCsv(xs, tan, eps, outputDir.resolve("tan.csv"));
        writeCsv(xs, cot, eps, outputDir.resolve("cot.csv"));
        writeCsv(xs, sec, eps, outputDir.resolve("sec.csv"));

        writeCsv(xs, ln, eps, outputDir.resolve("ln.csv"));
        writeCsv(xs, log2, eps, outputDir.resolve("log2.csv"));
        writeCsv(xs, log3, eps, outputDir.resolve("log3.csv"));
        writeCsv(xs, log10, eps, outputDir.resolve("log10.csv"));

        writeCsv(xs, system, eps, outputDir.resolve("system_expected.csv"));
    }

    private static void writeCsv(
            List<BigDecimal> xs,
            MathFunction fn,
            BigDecimal eps,
            Path path
    ) throws Exception {

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
