package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class LoessInterpolatorTest_testIncreasingBandwidthIncreasesSmoothness {

    private static final int NUM_POINTS = 100;
    private static final double X_NOISE = 0.1;
    private static final double Y_NOISE = 0.1;
    private static final double[] BANDWIDTHS = { 0.1, 0.5, 1.0 };
    private static final int ROBUSTNESS_ITERS = 4;
    private static final double ACCURACY = 1e-12;

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
    public void testIncreasingBandwidthIncreasesSmoothness() {
        double[] xval = new double[NUM_POINTS];
        double[] yval = new double[NUM_POINTS];
        generateSineData(xval, yval, X_NOISE, Y_NOISE);

        double[] roughnessByBandwidth = calculateRoughnessForEachBandwidth(xval, yval);

        for (int i = 1; i < roughnessByBandwidth.length; ++i) {
            Assert.assertTrue(roughnessByBandwidth[i] < roughnessByBandwidth[i - 1]);
        }
    }

    private double[] calculateRoughnessForEachBandwidth(double[] xval, double[] yval) {
        double[] roughnessByBandwidth = new double[BANDWIDTHS.length];
        for (int i = 0; i < BANDWIDTHS.length; i++) {
            LoessInterpolator interpolator =
                    new LoessInterpolator(BANDWIDTHS[i], ROBUSTNESS_ITERS, ACCURACY);
            double[] smoothedValues = interpolator.smooth(xval, yval);
            roughnessByBandwidth[i] = sumSquaredAdjacentDifferences(smoothedValues);
        }
        return roughnessByBandwidth;
    }

    private double sumSquaredAdjacentDifferences(double[] values) {
        double total = 0;
        for (int i = 1; i < values.length; ++i) {
            total += JdkMath.pow(values[i] - values[i - 1], 2);
        }
        return total;
    }
}
