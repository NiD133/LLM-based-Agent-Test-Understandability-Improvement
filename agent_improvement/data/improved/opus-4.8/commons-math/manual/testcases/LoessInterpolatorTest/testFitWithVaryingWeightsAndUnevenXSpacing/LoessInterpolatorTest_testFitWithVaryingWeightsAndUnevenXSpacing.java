package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-1379: verifies that {@link LoessInterpolator#smooth}
 * produces the expected LOESS fit when the sample points have uneven x-spacing
 * and per-point weights that vary.
 *
 * <p>The expected values ({@code EXPECTED_SMOOTHED_Y}) were produced by R using:
 * <pre>
 * predict(loess(y ~ x, data.frame(x = xval, y = yval), weights,
 *               span = 0.35, degree = 1, family = "symmetric",
 *               control = loess.control(iterations = 1, surface = "direct")))
 * </pre>
 */
public class LoessInterpolatorTest_testFitWithVaryingWeightsAndUnevenXSpacing {

    /** Fraction of points used in each local regression (R's {@code span} / {@code degree=1}). */
    private static final double BANDWIDTH = 0.35;

    /**
     * Number of robustness (re-weighting) iterations performed in addition to the
     * initial fit. R counts the initial fit as an iteration, so R's
     * {@code iterations = 1} corresponds to 0 extra robustness iterations here.
     */
    private static final int ROBUSTNESS_ITERATIONS = 0;

    /** Convergence accuracy for the local regressions. */
    private static final double ACCURACY = 1e-12;

    /** Tolerance when comparing the smoothed output against the reference values. */
    private static final double TOLERANCE = 1e-8;

    // MATH-1379
    @Test
    public void testFitWithVaryingWeightsAndUnevenXSpacing() {
        // Unevenly spaced abscissae (strictly increasing).
        final double[] xval = { 0.1, 0.12, 0.23, 0.4, 0.57, 0.7, 0.87, 1.3, 1.9, 2.2,
                                2.3, 2.65, 3.0, 3.1, 3.5, 4.6, 4.7, 5.8, 5.95, 6.1 };
        final double[] yval = { 0.47, 0.48, 0.55, 0.56, -0.08, -0.04, -0.07, -0.07, -0.56, -0.46,
                                -0.56, -0.52, -3.03, -3.08, -3.09, -3.04, 3.54, 3.46, 3.36, 3.35 };
        // Per-point weights; several points are down-weighted.
        final double[] weights = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                                   1, 0.8, 0.5, 0.5, 0.8, 0.8, 0.5, 0.5, 0.9, 1 };
        // Reference LOESS fit computed independently in R (see class Javadoc).
        final double[] expectedSmoothedY = {
            0.556184894, 0.541907126, 0.455059334, 0.303681477, 0.142126445,
            0.002615653, -0.031178445, -0.187124310, -0.406403569, -0.531957113,
            -0.669978426, -1.411039850, -2.225022609, -2.463874517, -3.298767758,
            -0.506116921, -0.231242991, 2.873755984, 3.295897106, 3.715495321 };

        final LoessInterpolator interpolator =
            new LoessInterpolator(BANDWIDTH, ROBUSTNESS_ITERATIONS, ACCURACY);

        final double[] smoothedY = interpolator.smooth(xval, yval, weights);

        Assert.assertEquals(xval.length, smoothedY.length);
        for (int i = 0; i < smoothedY.length; ++i) {
            Assert.assertEquals(expectedSmoothedY[i], smoothedY[i], TOLERANCE);
        }
    }
}
