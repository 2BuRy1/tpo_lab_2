package brizgy.tpolab2;

import brizgy.tpolab2.func.MathFunction;
import brizgy.tpolab2.util.CsvExporter;
import brizgy.tpolab2.util.ModuleFactory;

import java.math.BigDecimal;
import java.nio.file.Path;

public class Export {
    public static void main(String[] args) throws Exception {

        String module = "sin";
        MathFunction fn = ModuleFactory.build(module, new BigDecimal("1E-20"));

        CsvExporter.export(
                fn,
                new BigDecimal("-3"),
                new BigDecimal("3"),
                new BigDecimal("0.1"),
                new BigDecimal("1E-20"),
                Path.of("out_" + module + ".csv"),
                CsvExporter.OnErrorMode.SKIP_ROW
        );

        System.out.println("done");
    }
}