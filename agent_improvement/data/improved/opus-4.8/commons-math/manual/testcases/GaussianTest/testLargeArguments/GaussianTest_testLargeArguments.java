package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that the standard Gaussian (mean 0, standard deviation 1) evaluates
 * to exactly 0 for arguments whose magnitude is far outside the region where the
 * bell curve carries any representable density.
 */
public class GaussianTest_testLargeArguments {

    /** Tolerance of 0 demands an exact match with the expected value. */
    private static final double EXACT = 0;

    @Test
    public void testLargeArguments() {
        final UnivariateFunction standardGaussian = new Gaussian();

        // Arguments far below the mean underflow the density to exactly 0.
        Assert.assertEquals(0, standardGaussian.value(Double.NEGATIVE_INFINITY), EXACT);
        Assert.assertEquals(0, standardGaussian.value(-Double.MAX_VALUE), EXACT);
        Assert.assertEquals(0, standardGaussian.value(-1e2), EXACT);

        // Arguments far above the mean underflow the density to exactly 0.
        Assert.assertEquals(0, standardGaussian.value(1e2), EXACT);
        Assert.assertEquals(0, standardGaussian.value(Double.MAX_VALUE), EXACT);
        Assert.assertEquals(0, standardGaussian.value(Double.POSITIVE_INFINITY), EXACT);
    }
}
