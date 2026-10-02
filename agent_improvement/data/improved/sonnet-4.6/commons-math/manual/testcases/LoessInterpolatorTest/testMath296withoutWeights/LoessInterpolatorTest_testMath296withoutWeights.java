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
 * Regression test for MATH-296: verifies that LoessInterpolator produces
 * results consistent with R's loess() implementation when no per-point
 * weights are supplied.
 */
public class LoessInterpolatorTest_testMath296withoutWeights {

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
     * Feeds a known noisy dataset to LoessInterpolator (bandwidth=0.3,
     * robustness iterations=4) and checks that every smoothed value matches
     * the corresponding reference value produced by R to within 0.02.
     *
     * The reference values (yref) were computed by R's loess() with the same
     * bandwidth and rounded to three decimal places.
     */
    @Test
    public void testMath296withoutWeights() {
        // Evenly-spaced x-coordinates from 0.1 to 2.0
        double[] xval = {
            0.1, 0.2, 0.3, 0.4, 0.5,
            0.6, 0.7, 0.8, 0.9, 1.0,
            1.1, 1.2, 1.3, 1.4, 1.5,
            1.6, 1.7, 1.8, 1.9, 2.0
        };

        // Noisy observed y-values (approximate sine wave with outliers near x=1.3-1.6 and x=1.7-2.0)
        double[] yval = {
             0.47,  0.48,  0.55,  0.56, -0.08,
            -0.04, -0.07, -0.07, -0.56, -0.46,
            -0.56, -0.52, -3.03, -3.08, -3.09,
            -3.04,  3.54,  3.46,  3.36,  3.35
        };

        // Expected smoothed values from R's loess(), rounded to 0.001
        double[] expectedSmoothedValues = {
             0.461,  0.499,  0.541,  0.308,  0.175,
            -0.042, -0.072, -0.196, -0.311, -0.446,
            -0.557, -1.497, -2.133, -3.080, -3.090,
            -0.621,  0.982,  3.449,  3.389,  3.336
        };

        // bandwidth=0.3 means 30% of points are used in each local regression;
        // 4 robustness iterations down-weight outliers; accuracy=1e-12
        LoessInterpolator loessInterpolator = new LoessInterpolator(0.3, 4, 1e-12);
        double[] smoothedValues = loessInterpolator.smooth(xval, yval);

        // The smoother must return exactly one value per input point
        Assert.assertEquals(xval.length, smoothedValues.length);

        // Each smoothed value must be within 0.02 of the R reference
        double toleranceMatchingROutput = 0.02;
        for (int i = 0; i < smoothedValues.length; ++i) {
            Assert.assertEquals(expectedSmoothedValues[i], smoothedValues[i], toleranceMatchingROutput);
        }
    }
}
