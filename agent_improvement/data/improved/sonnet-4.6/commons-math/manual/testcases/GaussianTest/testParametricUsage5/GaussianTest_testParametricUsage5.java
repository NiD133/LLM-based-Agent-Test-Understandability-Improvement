package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

/**
 * Tests that {@link Gaussian.Parametric#gradient} throws {@link DimensionMismatchException}
 * when given a parameter array with fewer than the required three elements
 * (norm, mean, sigma).
 */
public class GaussianTest_testParametricUsage5 {

    /**
     * The Gaussian parametric model requires exactly three parameters: [norm, mean, sigma].
     * Passing an array with only one element should trigger a DimensionMismatchException.
     */
    @Test(expected = DimensionMismatchException.class)
    public void testParametricUsage5() {
        final Gaussian.Parametric gaussian = new Gaussian.Parametric();

        // Provide only one parameter instead of the required three [norm, mean, sigma].
        // This must throw DimensionMismatchException.
        gaussian.gradient(0, new double[] { 0 });
    }
}
