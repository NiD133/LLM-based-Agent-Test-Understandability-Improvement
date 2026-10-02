package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class LoessInterpolatorTest_testIncreasingRobustnessItersIncreasesSmoothnessWithOutliers {

    /**
     * Fills {@code xval}/{@code yval} with a noisy sine wave sampled over one
     * full period. {@code xnoise} jitters the spacing between successive x
     * values; {@code ynoise} adds random vertical noise to each y value.
     */
    private void generateSineData(double[] xval, double[] yval, double xnoise, double ynoise) {
        final double meanSpacing = 2 * JdkMath.PI / xval.length;
        double x = 0;
        for (int i = 0; i < xval.length; ++i) {
            xval[i] = x;
            yval[i] = JdkMath.sin(x) + (2 * JdkMath.random() - 1) * ynoise;
            x += meanSpacing * (1 + (2 * JdkMath.random() - 1) * xnoise);
        }
    }

    /**
     * Sums the absolute differences between consecutive smoothed values. This is
     * the total variation of the curve and serves as a measure of roughness:
     * a smoother curve has a smaller value.
     */
    private double totalVariation(double[] values) {
        double variation = 0;
        for (int j = 1; j < values.length; ++j) {
            variation += JdkMath.abs(values[j] - values[j - 1]);
        }
        return variation;
    }

    @Test
    public void testIncreasingRobustnessItersIncreasesSmoothnessWithOutliers() {
        final int numPoints = 100;
        final double xnoise = 0.1;
        final double ynoise = 0.1;

        final double[] xval = new double[numPoints];
        final double[] yval = new double[numPoints];
        generateSineData(xval, yval, xnoise, ynoise);

        // Introduce a couple of large outliers that the robustness iterations
        // should progressively suppress.
        yval[numPoints / 3] *= 100;
        yval[2 * numPoints / 3] *= -100;

        // Smooth the same data with an increasing number of robustness
        // iterations and record how rough each resulting curve is.
        final int maxRobustnessIters = 4;
        final double bandwidth = 0.3;
        final double accuracy = 1e-12;
        final double[] roughness = new double[maxRobustnessIters];
        for (int iters = 0; iters < maxRobustnessIters; iters++) {
            LoessInterpolator li = new LoessInterpolator(bandwidth, iters, accuracy);
            double[] smoothed = li.smooth(xval, yval);
            roughness[iters] = totalVariation(smoothed);
        }

        // More robustness iterations should yield a smoother (less rough) curve.
        for (int iters = 1; iters < roughness.length; ++iters) {
            Assert.assertTrue(roughness[iters] < roughness[iters - 1]);
        }
    }
}
