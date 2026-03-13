package unit.log;

import brizgy.tpolab2.log.Ln;
import brizgy.tpolab2.log.Log;
import brizgy.tpolab2.func.MathFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static util.BdAsserts.*;

public class LogTest {

    private final MathFunction ln = new Ln();
    private final BigDecimal eps = new BigDecimal("1E-6");
    private final BigDecimal tol = new BigDecimal("1E-3");

    private final MathFunction log2 = new Log(ln, new BigDecimal("2"));
    private final MathFunction log3 = new Log(ln, new BigDecimal("3"));
    private final MathFunction log10 = new Log(ln, new BigDecimal("10"));

    @ParameterizedTest
    @CsvSource({
            "0.1, -3.3219280948873623473",
            "0.2, -2.3219280948873623479",
            "0.5, -1",
            "2.0, 1",
            "3.0, 1.5849625007211561815",
            "10.0, 3.3219280948873623473"
    })
    void log2_matches_reference(String xs, String expectedS) {
        BigDecimal actual = log2.calc(new BigDecimal(xs), eps);
        BigDecimal expected = new BigDecimal(expectedS);
        assertClose(expected, actual, tol);
    }

    @ParameterizedTest
    @CsvSource({
            "0.1, -2.0959032742893846039",
            "0.2, -1.4649735207179271672",
            "0.5, -0.63092975357145743708",
            "2.0, 0.63092975357145743708",
            "3.0, 1",
            "10.0, 2.0959032742893846039"
    })
    void log3_matches_reference(String xs, String expectedS) {
        BigDecimal actual = log3.calc(new BigDecimal(xs), eps);
        BigDecimal expected = new BigDecimal(expectedS);
        assertClose(expected, actual, tol);
    }

    @ParameterizedTest
    @CsvSource({
            "0.1, -1",
            "0.2, -0.69897000433601880491",
            "0.5, -0.30102999566398119526",
            "2.0, 0.30102999566398119526",
            "3.0, 0.47712125471966243738",
            "10.0, 1"
    })
    void log10_matches_reference(String xs, String expectedS) {
        BigDecimal actual = log10.calc(new BigDecimal(xs), eps);
        BigDecimal expected = new BigDecimal(expectedS);
        assertClose(expected, actual, tol);
    }

    @Test
    void log_throws_for_non_positive() {
        assertThrows(ArithmeticException.class, () -> log2.calc(BigDecimal.ZERO, eps));
        assertThrows(ArithmeticException.class, () -> log2.calc(new BigDecimal("-1"), eps));

        assertThrows(ArithmeticException.class, () -> log3.calc(BigDecimal.ZERO, eps));
        assertThrows(ArithmeticException.class, () -> log3.calc(new BigDecimal("-1"), eps));

        assertThrows(ArithmeticException.class, () -> log10.calc(BigDecimal.ZERO, eps));
        assertThrows(ArithmeticException.class, () -> log10.calc(new BigDecimal("-1"), eps));
    }

    @Test
    void log_uses_ln_dependency_with_mock() {
        MathFunction lnMock = mock(MathFunction.class);
        MathFunction log2 = new Log(lnMock, new BigDecimal("2"));

        BigDecimal x = new BigDecimal("8");
        BigDecimal base = new BigDecimal("2");

        when(lnMock.calc(x, eps)).thenReturn(new BigDecimal("6"));
        when(lnMock.calc(base, eps)).thenReturn(new BigDecimal("2"));

        BigDecimal actual = log2.calc(x, eps);

        assertClose(new BigDecimal("3"), actual, new BigDecimal("1E-12"));
        verify(lnMock).calc(x, eps);
        verify(lnMock).calc(base, eps);
    }
}
