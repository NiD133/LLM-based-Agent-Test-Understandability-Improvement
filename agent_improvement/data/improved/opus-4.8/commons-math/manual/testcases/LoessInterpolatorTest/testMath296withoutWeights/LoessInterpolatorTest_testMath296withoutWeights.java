package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-296: {@link LoessInterpolator#smooth(double[], double[])}
 * must produce a smoothed curve that matches the reference output computed by R,
 * even when the input data contains sharp outliers.
 */
public class LoessInterpolatorTest_testMath296withoutWeights {

    /** Bandwidth: fraction of points used in each local regression. */
    private static final double BANDWIDTH = 0.3;
    /** Number of robustness (outlier-resistance) iterations. */
    private static final int ROBUSTNESS_ITERATIONS = 4;
    /** Convergence accuracy for the robustness iterations. */
    private static final double ACCURACY = 1e-12;
    /** Tolerance allowed between the smoothed result and the R reference values. */
    private static final double TOLERANCE = 0.02;

    @Test
    public void testMath296withoutWeights() {
        double[] xval = {
            0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0,
            1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0
        };
        double[] yval = {
            0.47, 0.48, 0.55, 0.56, -0.08, -0.04, -0.07, -0.07, -0.56, -0.46,
            -0.56, -0.52, -3.03, -3.08, -3.09, -3.04, 3.54, 3.46, 3.36, 3.35
        };
        // Expected smoothed values, taken from R and rounded to .001.
        double[] expectedSmoothed = {
            0.461, 0.499, 0.541, 0.308, 0.175, -0.042, -0.072, -0.196, -0.311, -0.446,
            -0.557, -1.497, -2.133, -3.08, -3.09, -0.621, 0.982, 3.449, 3.389, 3.336
        };

        LoessInterpolator interpolator =
            new LoessInterpolator(BANDWIDTH, ROBUSTNESS_ITERATIONS, ACCURACY);
        double[] smoothed = interpolator.smooth(xval, yval);

        Assert.assertEquals(xval.length, smoothed.length);
        for (int i = 0; i < smoothed.length; ++i) {
            Assert.assertEquals(expectedSmoothed[i], smoothed[i], TOLERANCE);
        }
    }
}
