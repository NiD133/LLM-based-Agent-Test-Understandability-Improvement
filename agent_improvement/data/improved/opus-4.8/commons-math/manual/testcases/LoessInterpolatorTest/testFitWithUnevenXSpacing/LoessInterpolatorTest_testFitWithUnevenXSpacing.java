package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-1379: LOESS smoothing must produce correct results
 * even when the x-coordinates of the sample points are unevenly spaced.
 */
public class LoessInterpolatorTest_testFitWithUnevenXSpacing {

    @Test
    public void testFitWithUnevenXSpacing() {
        // Sample points with deliberately uneven spacing between consecutive x values.
        final double[] xValues = {
            0.1, 0.12, 0.23, 0.4, 0.57, 0.7, 0.87, 1.3, 1.9, 2.2,
            2.3, 2.65, 3.0, 3.1, 3.5, 4.6, 4.7, 5.8, 5.95, 6.1
        };
        final double[] yValues = {
            0.47, 0.48, 0.55, 0.56, -0.08, -0.04, -0.07, -0.07, -0.56, -0.46,
            -0.56, -0.52, -3.03, -3.08, -3.09, -3.04, 3.54, 3.46, 3.36, 3.35
        };

        // Expected smoothed values, taken from the equivalent computation in R:
        //   predict(loess(y ~ x, data.frame(x = xValues, y = yValues),
        //                 span = 0.35, degree = 1, family = "symmetric",
        //                 control = loess.control(iterations = 1, surface = "direct")))
        final double[] expectedSmoothed = {
            0.556184894, 0.541907126, 0.455059334, 0.303681477, 0.142126445,
            0.002615653, -0.031178445, -0.187124310, -0.405235207, -0.535023851,
            -0.706801740, -1.466740294, -2.349248503, -2.596576469, -3.354222419,
            0.086206868, 0.320251370, 3.064778450, 3.426179479, 3.783500164
        };

        // LoessInterpolator parameters: bandwidth (span) = 0.35, robustness iterations = 0,
        // accuracy = 1e-12. Note: R counts all iterations, whereas LoessInterpolator's
        // robustness iterations are in addition to the initial fit, so 0 here matches
        // R's iterations = 1.
        final double bandwidth = 0.35;
        final int robustnessIterations = 0;
        final double accuracy = 1e-12;
        final LoessInterpolator interpolator =
            new LoessInterpolator(bandwidth, robustnessIterations, accuracy);

        final double[] smoothed = interpolator.smooth(xValues, yValues);

        final double tolerance = 1e-8;
        Assert.assertEquals(xValues.length, smoothed.length);
        for (int i = 0; i < smoothed.length; ++i) {
            Assert.assertEquals(expectedSmoothed[i], smoothed[i], tolerance);
        }
    }
}
