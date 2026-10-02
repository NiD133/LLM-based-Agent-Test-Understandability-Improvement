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

public class LoessInterpolatorTest_testIncreasingBandwidthIncreasesSmoothness {

    /**
     * Fills xval with evenly-spaced (plus noise) angles over [0, 2π)
     * and yval with sin(x) plus additive noise.
     */
    private void generateNoisySineData(double[] xval, double[] yval,
                                       double xnoise, double ynoise) {
        double dx = 2 * JdkMath.PI / xval.length;
        double x = 0;
        for (int i = 0; i < xval.length; ++i) {
            xval[i] = x;
            yval[i] = JdkMath.sin(x) + (2 * JdkMath.random() - 1) * ynoise;
            x += dx * (1 + (2 * JdkMath.random() - 1) * xnoise);
        }
    }

    /**
     * Measures how "rough" a smoothed curve is by summing squared
     * first differences between consecutive output values.
     * A perfectly smooth constant function scores 0; a jagged curve scores high.
     */
    private double computeRoughness(double[] smoothed) {
        double roughness = 0;
        for (int i = 1; i < smoothed.length; ++i) {
            roughness += JdkMath.pow(smoothed[i] - smoothed[i - 1], 2);
        }
        return roughness;
    }

    @Test
    public void testIncreasingBandwidthIncreasesSmoothness() {
        // Generate a noisy sine wave as the input signal
        int numPoints = 100;
        double[] xval = new double[numPoints];
        double[] yval = new double[numPoints];
        generateNoisySineData(xval, yval, /* xnoise */ 0.1, /* ynoise */ 0.1);

        // A larger LOESS bandwidth considers more neighbours, producing a smoother fit.
        // Verify that roughness strictly decreases as bandwidth increases.
        double[] bandwidths = { 0.1, 0.5, 1.0 };
        double[] roughnessScores = new double[bandwidths.length];

        for (int i = 0; i < bandwidths.length; i++) {
            LoessInterpolator interpolator = new LoessInterpolator(bandwidths[i], 4, 1e-12);
            double[] smoothed = interpolator.smooth(xval, yval);
            roughnessScores[i] = computeRoughness(smoothed);
        }

        for (int i = 1; i < roughnessScores.length; i++) {
            Assert.assertTrue(roughnessScores[i] < roughnessScores[i - 1]);
        }
    }
}
