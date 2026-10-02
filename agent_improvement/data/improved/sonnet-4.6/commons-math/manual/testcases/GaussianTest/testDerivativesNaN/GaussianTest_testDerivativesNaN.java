package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

public class GaussianTest_testDerivativesNaN {

    @Test
    public void testDerivativesNaN() {
        // A Gaussian with an extremely small standard deviation (near-zero width)
        // evaluated at NaN should produce NaN for all derivative orders.
        final Gaussian gaussian = new Gaussian(0, 1e-50);

        // Compute the value and all derivatives up to order 5 at the point NaN.
        // DerivativeStructure(nVars=1, order=5, varIndex=0, value=NaN)
        final int derivativeOrder = 5;
        final DerivativeStructure inputNaN = new DerivativeStructure(1, derivativeOrder, 0, Double.NaN);
        final DerivativeStructure result = gaussian.value(inputNaN);

        // Every partial derivative (0th through 5th order) must be NaN
        // because propagating NaN through any arithmetic yields NaN.
        for (int order = 0; order <= result.getOrder(); ++order) {
            Assert.assertTrue(
                "Expected NaN for derivative order " + order,
                Double.isNaN(result.getPartialDerivative(order))
            );
        }
    }
}
