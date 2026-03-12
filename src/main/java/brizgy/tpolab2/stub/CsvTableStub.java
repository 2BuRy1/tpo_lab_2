package brizgy.tpolab2.stub;

import brizgy.tpolab2.func.MathFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CsvTableStub implements MathFunction {

    private final Map<String, BigDecimal> table = new HashMap<>();

    public CsvTableStub(Path path) throws IOException {

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.startsWith("x") || line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(",");

                BigDecimal x = new BigDecimal(parts[0].trim());
                BigDecimal value = new BigDecimal(parts[1].trim());

                table.put(key(x), value);
            }
        }
    }

    @Override
    public BigDecimal calc(BigDecimal x, BigDecimal eps) {

        BigDecimal value = table.get(key(x));

        if (value == null) {
            throw new IllegalArgumentException("Value not found for x=" + x);
        }

        return value;
    }

    private String key(BigDecimal x) {
        return x.stripTrailingZeros().toPlainString();
    }
}
