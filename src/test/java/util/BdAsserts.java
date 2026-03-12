package util;

import java.math.BigDecimal;

public final class BdAsserts {
    private BdAsserts() {}

    public static void assertClose(BigDecimal expected, BigDecimal actual, BigDecimal tol) {
        BigDecimal diff = expected.subtract(actual).abs();
        if (diff.compareTo(tol) > 0) {
            throw new AssertionError("Expected " + expected + ", actual " + actual + ", diff " + diff);
        }
    }

    public static BigDecimal bd(double x) {
        return BigDecimal.valueOf(x);
    }
}