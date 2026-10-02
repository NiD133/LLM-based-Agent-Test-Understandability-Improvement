package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link Sigmoid} reports the correct first derivative at x = 0.
 *
 * <p>The default sigmoid maps to the range (0, 1), so f(0) = 0.5. Its derivative
 * is f(x) * (1 - f(x)); evaluated at x = 0 this gives 0.5 * 0.5 = 0.25.</p>
 */
public class SigmoidTest_testDerivative {

    @Test
    public void testDerivative() {
        final Sigmoid sigmoid = new Sigmoid();

        // Evaluate the sigmoid at x = 0 while tracking first-order derivatives:
        // 1 input variable, differentiation order 1, variable index 0, value 0.0.
        final DerivativeStructure inputAtZero = new DerivativeStructure(1, 1, 0, 0.0);
        final DerivativeStructure result = sigmoid.value(inputAtZero);

        final double expectedFirstDerivative = 0.25;
        final double firstDerivative = result.getPartialDerivative(1);
        Assert.assertEquals(expectedFirstDerivative, firstDerivative, 0);
    }
}
