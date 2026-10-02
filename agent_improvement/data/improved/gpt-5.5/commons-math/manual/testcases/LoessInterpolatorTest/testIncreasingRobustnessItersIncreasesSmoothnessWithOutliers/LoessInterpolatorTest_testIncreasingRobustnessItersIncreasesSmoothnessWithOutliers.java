package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class LoessInterpolatorTest_testIncreasingRobustnessItersIncreasesSmoothnessWithOutliers {

    private static final int NUM_POINTS = 100;
    private static final double X_NOISE = 0.1;
    private static final double Y_NOISE = 0.1;
    private static final double BANDWIDTH = 0.3;
    private static final double ACCURACY = 1e-12;
    private static final int MAX_ROBUSTNESS_ITERS_EXCLUSIVE = 4;

    private void generateSineData(double[] xval, double[] yval, double xnoise, double ynoise) {
        double dx = 2 * JdkMath.PI / xval.length;
        double x = 0;
        for (int i = 0; i < xval.length; ++i) {
            xval[i] = x;
            yval[i] = JdkMath.sin(x) + (2 * JdkMath.random() - 1) * ynoise;
            x += dx * (1 + (2 * JdkMath.random() - 1) * xnoise);
        }
    }

    @Test
    public void testIncreasingRobustnessItersIncreasesSmoothnessWithOutliers() {
        double[] xval = new double[NUM_POINTS];
        double[] yval = new double[NUM_POINTS];
        generateSineData(xval, yval, X_NOISE, Y_NOISE);

        yval[NUM_POINTS / 3] *= 100;
        yval[2 * NUM_POINTS / 3] *= -100;

        double[] variances = new double[MAX_ROBUSTNESS_ITERS_EXCLUSIVE];
        for (int robustnessIters = 0;
             robustnessIters < MAX_ROBUSTNESS_ITERS_EXCLUSIVE;
             robustnessIters++) {
            LoessInterpolator interpolator =
                    new LoessInterpolator(BANDWIDTH, robustnessIters, ACCURACY);
            double[] smoothed = interpolator.smooth(xval, yval);
            variances[robustnessIters] = totalVariation(smoothed);
        }

        for (int i = 1; i < variances.length; ++i) {
            Assert.assertTrue(variances[i] < variances[i - 1]);
        }
    }

    private double totalVariation(double[] values) {
        double variation = 0;
        for (int i = 1; i < values.length; ++i) {
            variation += JdkMath.abs(values[i] - values[i - 1]);
        }
        return variation;
    }
}
