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

public class LoessInterpolatorTest_testOnStraightLine {

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
     * Verifies that LOESS smoothing of a perfectly linear dataset (y = 2x)
     * reproduces the original values within floating-point tolerance.
     *
     * A straight line has zero curvature, so any local polynomial fit should
     * pass through every point exactly. The 1e-8 tolerance accounts for
     * numerical precision in the weighted-least-squares computation.
     */
    @Test
    public void testOnStraightLine() {
        // Input: five evenly-spaced points on the line y = 2x
        double[] xval = { 1, 2, 3, 4, 5 };
        double[] yval = { 2, 4, 6, 8, 10 };

        // bandwidth=0.6 uses 60% of points for each local fit;
        // robustness=2 iterations; delta=1e-12 convergence threshold
        double bandwidth  = 0.6;
        int    robustnessIters = 2;
        double delta      = 1e-12;
        LoessInterpolator interpolator = new LoessInterpolator(bandwidth, robustnessIters, delta);

        double[] smoothed = interpolator.smooth(xval, yval);

        // The smoothed array must have one value per input point
        Assert.assertEquals("smoothed array length should match input length",
                xval.length, smoothed.length);

        // For a straight line, LOESS should recover each original y-value
        double tolerance = 1e-8;
        for (int i = 0; i < xval.length; ++i) {
            Assert.assertEquals(
                    "smoothed value at x=" + xval[i] + " should equal original y=" + yval[i],
                    yval[i], smoothed[i], tolerance);
        }
    }
}
