package integration;

import bilyardvmetro.tpolab2.func.MathFunction;
import bilyardvmetro.tpolab2.system.FuncSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuncSystemIntegrationTest {

    @Mock
    MathFunction sin;
    @Mock
    MathFunction cos;
    @Mock
    MathFunction tan;
    @Mock
    MathFunction cot;

    @Mock
    MathFunction ln;

    @Mock
    MathFunction log2;
    @Mock
    MathFunction log3;
    @Mock
    MathFunction log10;

    @Test
    void trig_branch_call_order() {

        BigDecimal x = new BigDecimal("-0.5");
        BigDecimal eps = new BigDecimal("1E-20");

        when(sin.calc(x, eps)).thenReturn(new BigDecimal("2"));
        when(cos.calc(x, eps)).thenReturn(BigDecimal.ONE);
        when(tan.calc(x, eps)).thenReturn(BigDecimal.ONE);
        when(cot.calc(x, eps)).thenReturn(BigDecimal.ONE);

        FuncSystem system =
                new FuncSystem(sin, cos, tan, cot, ln, log2, log3, log10);

        system.calc(x, eps);

        InOrder order = inOrder(sin, cos, tan, cot);

        order.verify(sin).calc(x, eps);
        order.verify(cos).calc(x, eps);
        order.verify(tan).calc(x, eps);
        order.verify(cot).calc(x, eps);

        verifyNoInteractions(ln, log2, log3, log10);
    }

    @Test
    void log_branch_call_order() {

        BigDecimal x = new BigDecimal("2.0");
        BigDecimal eps = new BigDecimal("1E-20");

        when(ln.calc(x, eps)).thenReturn(BigDecimal.ONE);
        when(log2.calc(x, eps)).thenReturn(BigDecimal.ONE);
        when(log3.calc(x, eps)).thenReturn(BigDecimal.ONE);
        when(log10.calc(x, eps)).thenReturn(BigDecimal.ONE);

        FuncSystem system =
                new FuncSystem(sin, cos, tan, cot, ln, log2, log3, log10);

        system.calc(x, eps);

        InOrder order = inOrder(ln, log2, log3, log10);

        order.verify(ln).calc(x, eps);
        order.verify(log2).calc(x, eps);
        order.verify(log3).calc(x, eps);
        order.verify(log10).calc(x, eps);

        verifyNoInteractions(sin, cos, tan, cot);
    }
}
