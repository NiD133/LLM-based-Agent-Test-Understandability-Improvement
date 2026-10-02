package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.analysis.differentiation.UnivariateDifferentiableFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link Gaussian} computes the correct value and successive
 * partial derivatives (up to order 4) when evaluated with a
 * {@link DerivativeStructure}.
 */
public class GaussianTest_testDerivatives {

    /** Tolerance: results must match the expected values to within one ULP. */
    private static final double TOLERANCE = Math.ulp(1d);

    @Test
    public void testDerivatives() {
        // Gaussian defined by normalization = 2.0, mean = 0.9, sigma = 3.0.
        final UnivariateDifferentiableFunction gaussian = new Gaussian(2.0, 0.9, 3.0);

        // Evaluate at x = 1.1 with derivatives requested up to order 4
        // (1 variable, max order 4, variable index 0, value 1.1).
        final DerivativeStructure x = new DerivativeStructure(1, 4, 0, 1.1);
        final DerivativeStructure result = gaussian.value(x);

        // Function value f(1.1).
        Assert.assertEquals(1.9955604901712128349, result.getValue(), TOLERANCE);

        // Successive derivatives f'(1.1) through f''''(1.1).
        Assert.assertEquals(-0.044345788670471396332, result.getPartialDerivative(1), TOLERANCE);
        Assert.assertEquals(-0.22074348138190206174, result.getPartialDerivative(2), TOLERANCE);
        Assert.assertEquals(0.014760030401924800557, result.getPartialDerivative(3), TOLERANCE);
        Assert.assertEquals(0.073253159785035691678, result.getPartialDerivative(4), TOLERANCE);
    }
}
