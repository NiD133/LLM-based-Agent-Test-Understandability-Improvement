package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NoDataException;
import org.apache.commons.math4.legacy.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.apache.commons.math4.legacy.exception.NumberIsTooSmallException;
import org.apache.commons.math4.legacy.exception.OutOfRangeException;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link LoessInterpolator} focusing on MATH-1379: fitting with
 * unevenly-spaced x values.
 */
public class LoessInterpolatorTest_testFitWithUnevenXSpacing {

    private void generateSineData(double[] xval, double[] yval, double xnoise, double ynoise) {
        double dx = 2 * JdkMath.PI / xval.length;
        double x = 0;
        for (int i = 0; i < xval.length; ++i) {
            xval[i] = x;
            yval[i] = JdkMath.sin(x) + (2 * JdkMath.random() - 1) * ynoise;
            x += dx * (1 + (2 * JdkMath.random() - 1) * xnoise);
        }
    }

    /**
     * Regression test for MATH-1379: verifies that LOESS smoothing of a dataset
     * with unevenly-spaced x values matches the output of R's loess() function.
     *
     * R command used to generate the reference values:
     *   predict(loess(y ~ x, data.frame(x=xval, y=yval),
     *                 span=0.35, degree=1, family="symmetric",
     *                 control=loess.control(iterations=1, surface="direct")))
     *
     * Note: R counts all iterations, whereas LoessInterpolator's robustness
     * iterations are in addition to the initial fit (hence iterations=1 means
     * zero robustness iterations in Java).
     */
    @Test
    public void testFitWithUnevenXSpacing() {
        // Unevenly-spaced x values spanning [0.1, 6.1]
        final double[] xval = {
            0.1,  0.12, 0.23, 0.4,  0.57,
            0.7,  0.87, 1.3,  1.9,  2.2,
            2.3,  2.65, 3.0,  3.1,  3.5,
            4.6,  4.7,  5.8,  5.95, 6.1
        };

        // Corresponding observed y values (noisy sine-like data)
        final double[] yval = {
             0.47,  0.48,  0.55,  0.56, -0.08,
            -0.04, -0.07, -0.07, -0.56, -0.46,
            -0.56, -0.52, -3.03, -3.08, -3.09,
            -3.04,  3.54,  3.46,  3.36,  3.35
        };

        // Expected smoothed values computed by R's loess() (see Javadoc above)
        final double[] yref = {
             0.556184894,  0.541907126,  0.455059334,  0.303681477,  0.142126445,
             0.002615653, -0.031178445, -0.187124310, -0.405235207, -0.535023851,
            -0.706801740, -1.466740294, -2.349248503, -2.596576469, -3.354222419,
             0.086206868,  0.320251370,  3.064778450,  3.426179479,  3.783500164
        };

        // Tolerance for floating-point comparison against R reference values
        final double tolerance = 1e-8;

        // LOESS parameters: bandwidth=0.35, 0 robustness iterations, accuracy=1e-12
        final double bandwidth          = 0.35;
        final int    robustnessIters    = 0;
        final double accuracy           = 1e-12;
        final LoessInterpolator loess = new LoessInterpolator(bandwidth, robustnessIters, accuracy);

        final double[] smoothed = loess.smooth(xval, yval);

        Assert.assertEquals("Number of smoothed values must equal number of input points",
                            xval.length, smoothed.length);

        for (int i = 0; i < smoothed.length; ++i) {
            Assert.assertEquals(
                    "Smoothed value at index " + i + " (x=" + xval[i] + ")",
                    yref[i], smoothed[i], tolerance);
        }
    }
}
