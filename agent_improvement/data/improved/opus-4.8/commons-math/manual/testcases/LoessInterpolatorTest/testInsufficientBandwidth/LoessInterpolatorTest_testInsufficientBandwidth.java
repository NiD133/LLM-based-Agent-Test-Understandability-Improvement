package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NumberIsTooSmallException;
import org.junit.Test;

/**
 * Verifies that {@link LoessInterpolator} rejects a bandwidth that is too small
 * for the given number of sample points.
 *
 * <p>The bandwidth fraction multiplied by the number of points determines how
 * many neighbouring points contribute to each local regression. With a
 * bandwidth of {@code 0.1} and only 12 points, that window collapses to a
 * single point, which is below the minimum the algorithm can work with, so
 * smoothing must fail fast with a {@link NumberIsTooSmallException}.</p>
 */
public class LoessInterpolatorTest_testInsufficientBandwidth {

    /** Bandwidth fraction so small that the local regression window is undersized. */
    private static final double TOO_SMALL_BANDWIDTH = 0.1;

    /** Number of robustness iterations (irrelevant to this failure case). */
    private static final int ROBUSTNESS_ITERATIONS = 3;

    /** Convergence accuracy (irrelevant to this failure case). */
    private static final double ACCURACY = 1e-12;

    @Test(expected = NumberIsTooSmallException.class)
    public void testInsufficientBandwidth() {
        final double[] xValues = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        final double[] yValues = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

        final LoessInterpolator interpolator =
                new LoessInterpolator(TOO_SMALL_BANDWIDTH, ROBUSTNESS_ITERATIONS, ACCURACY);

        // Expected to throw NumberIsTooSmallException because the bandwidth
        // window covers too few points to perform the regression.
        interpolator.smooth(xValues, yValues);
    }
}
