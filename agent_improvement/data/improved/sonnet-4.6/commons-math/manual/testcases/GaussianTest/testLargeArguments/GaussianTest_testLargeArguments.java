package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that the standard Gaussian (bell curve) correctly returns zero
 * for extreme input values far outside the bell's peak region.
 *
 * The default Gaussian has mean=0 and sigma=1, so the function value
 * approaches 0 as |x| grows large.
 */
public class GaussianTest_testLargeArguments {

    private static final double EXACT_ZERO = 0;
    private static final double ABSOLUTE_TOLERANCE = 0;

    @Test
    public void testLargeArguments() {
        final UnivariateFunction gaussian = new Gaussian();

        // Values at negative infinity and extremely large negative numbers
        // should evaluate to exactly 0, since e^(-x^2/2) → 0 as x → -∞
        Assert.assertEquals(EXACT_ZERO, gaussian.value(Double.NEGATIVE_INFINITY), ABSOLUTE_TOLERANCE);
        Assert.assertEquals(EXACT_ZERO, gaussian.value(-Double.MAX_VALUE), ABSOLUTE_TOLERANCE);

        // Large finite negative and positive values also underflow to 0
        // because the exponent -x^2/2 becomes so negative that the result
        // cannot be represented as a nonzero double
        Assert.assertEquals(EXACT_ZERO, gaussian.value(-1e2), ABSOLUTE_TOLERANCE);
        Assert.assertEquals(EXACT_ZERO, gaussian.value(1e2), ABSOLUTE_TOLERANCE);

        // Symmetric behaviour: large positive numbers mirror large negative ones
        Assert.assertEquals(EXACT_ZERO, gaussian.value(Double.MAX_VALUE), ABSOLUTE_TOLERANCE);
        Assert.assertEquals(EXACT_ZERO, gaussian.value(Double.POSITIVE_INFINITY), ABSOLUTE_TOLERANCE);
    }
}
