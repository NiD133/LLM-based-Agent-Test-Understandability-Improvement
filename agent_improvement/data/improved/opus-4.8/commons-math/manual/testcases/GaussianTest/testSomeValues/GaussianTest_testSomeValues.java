package org.apache.commons.math4.legacy.analysis.function;

import static org.junit.Assert.assertEquals;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Test;

/**
 * Verifies that a default {@link Gaussian} (mean = 0, standard deviation = 1)
 * evaluates to the expected value of the standard normal density function.
 */
public class GaussianTest_testSomeValues {

    /** Allowed numerical error: one unit in the last place of 1.0. */
    private static final double TOLERANCE = Math.ulp(1d);

    @Test
    public void testSomeValues() {
        final UnivariateFunction standardGaussian = new Gaussian();

        // At its mean (x = 0) the standard normal density equals 1 / sqrt(2 * pi).
        final double expectedPeakValue = 1 / JdkMath.sqrt(2 * Math.PI);

        assertEquals(expectedPeakValue, standardGaussian.value(0), TOLERANCE);
    }
}
