package bilyardvmetro.tpolab2.util;

import bilyardvmetro.tpolab2.func.MathFunction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvExporter {

    public enum OnErrorMode {
        SKIP_ROW,      // пропускать точки вне области определения
        WRITE_ERROR    // писать ERROR в value
    }

    public static void export(
            MathFunction fn,
            BigDecimal fromX,
            BigDecimal toX,
            BigDecimal step,
            BigDecimal eps,
            Path out,
            OnErrorMode onErrorMode
    ) throws IOException {

        if (step.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("step must be > 0");
        }
        if (fromX.compareTo(toX) > 0) {
            throw new IllegalArgumentException("fromX must be <= toX");
        }

        try (BufferedWriter w = Files.newBufferedWriter(out)) {
            w.write("x,value");
            w.newLine();

            for (BigDecimal x = fromX; x.compareTo(toX) <= 0; x = x.add(step)) {
                try {
                    var y = fn.calc(x, eps);
                    w.write(x.toPlainString());
                    w.write(",");
                    w.write(y.toPlainString());
                    w.newLine();
                } catch (Exception ex) {
                    if (onErrorMode == OnErrorMode.WRITE_ERROR) {
                        w.write(x.toPlainString());
                        w.write(",ERROR");
                        w.newLine();
                    }
                    // SKIP_ROW
                }
            }
        }
    }
}