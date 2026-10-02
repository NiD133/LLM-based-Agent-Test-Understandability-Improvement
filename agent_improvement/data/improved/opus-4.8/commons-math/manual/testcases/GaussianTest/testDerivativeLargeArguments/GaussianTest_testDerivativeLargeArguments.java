package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that the derivative of a Gaussian function flattens to zero once the
 * input moves far away from the curve's mean.
 *
 * <p>The Gaussian under test has mean 0 and an extremely small standard deviation
 * (1e-50), so its bell shape is essentially a spike at the origin. Anywhere else
 * the function is flat, meaning its first derivative should be exactly 0 for any
 * argument that is large in magnitude (including the floating-point extremes).</p>
 */
public class GaussianTest_testDerivativeLargeArguments {

    /** Mean of the Gaussian under test. */
    private static final double MEAN = 0;

    /** Standard deviation; tiny, so the bell collapses to a spike at the mean. */
    private static final double STANDARD_DEVIATION = 1e-50;

    /** Expected first derivative far from the mean: exactly zero. */
    private static final double EXPECTED_DERIVATIVE = 0;

    /** Exact-match tolerance; the derivative must be precisely zero. */
    private static final double EXACT_TOLERANCE = 0;

    /**
     * Asserts that the Gaussian's first derivative at {@code argument} is exactly zero.
     */
    private static void assertFirstDerivativeIsZero(final Gaussian gaussian, final double argument) {
        // Wrap the argument so the value() call also tracks its first derivative
        // (1 variable, order 1, variable index 0).
        final DerivativeStructure input = new DerivativeStructure(1, 1, 0, argument);
        final double firstDerivative = gaussian.value(input).getPartialDerivative(1);
        Assert.assertEquals(EXPECTED_DERIVATIVE, firstDerivative, EXACT_TOLERANCE);
    }

    @Test
    public void testDerivativeLargeArguments() {
        final Gaussian f = new Gaussian(MEAN, STANDARD_DEVIATION);

        // Every argument below is far from the mean, so the derivative must vanish.
        assertFirstDerivativeIsZero(f, Double.NEGATIVE_INFINITY);
        assertFirstDerivativeIsZero(f, -Double.MAX_VALUE);
        assertFirstDerivativeIsZero(f, -1e50);
        assertFirstDerivativeIsZero(f, -1e2);
        assertFirstDerivativeIsZero(f, 1e2);
        assertFirstDerivativeIsZero(f, 1e50);
        assertFirstDerivativeIsZero(f, Double.MAX_VALUE);
        assertFirstDerivativeIsZero(f, Double.POSITIVE_INFINITY);
    }
}
