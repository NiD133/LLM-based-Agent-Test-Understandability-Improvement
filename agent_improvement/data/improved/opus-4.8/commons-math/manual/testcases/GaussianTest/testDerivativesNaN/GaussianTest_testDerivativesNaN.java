package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that evaluating a {@link Gaussian} at a NaN input yields NaN for the
 * function value and for every partial derivative it computes.
 */
public class GaussianTest_testDerivativesNaN {

    /** Number of free variables in the {@link DerivativeStructure} (single-variable function). */
    private static final int VARIABLE_COUNT = 1;

    /** Highest derivative order requested from the {@link DerivativeStructure}. */
    private static final int DERIVATIVE_ORDER = 5;

    /** Index of the variable whose value is supplied below. */
    private static final int VARIABLE_INDEX = 0;

    @Test
    public void testDerivativesNaN() {
        // A Gaussian centred at 0 with an extremely small standard deviation.
        final Gaussian gaussian = new Gaussian(0, 1e-50);

        // Evaluate the Gaussian at x = NaN, requesting derivatives up to order 5.
        final DerivativeStructure inputAtNaN =
                new DerivativeStructure(VARIABLE_COUNT, DERIVATIVE_ORDER, VARIABLE_INDEX, Double.NaN);
        final DerivativeStructure result = gaussian.value(inputAtNaN);

        // The value and all partial derivatives must be NaN.
        for (int order = 0; order <= result.getOrder(); ++order) {
            Assert.assertTrue(
                    "Partial derivative of order " + order + " should be NaN",
                    Double.isNaN(result.getPartialDerivative(order)));
        }
    }
}
