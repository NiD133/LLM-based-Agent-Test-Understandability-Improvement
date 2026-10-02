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

    /** Number of (x, y) samples drawn from the noisy sine curve. */
    private static final int NUM_POINTS = 100;

    /** Relative jitter applied to the spacing between consecutive x values. */
    private static final double X_NOISE = 0.1;

    /** Amplitude of the random noise added to each y value. */
    private static final double Y_NOISE = 0.1;

    /** LOESS robustness iterations and accuracy, held constant across all runs. */
    private static final int ROBUSTNESS_ITERS = 4;
    private static final double ACCURACY = 1e-12;

    /**
     * Fills {@code xval}/{@code yval} with one period of a sine wave.
     * The x spacing and the y values are each perturbed by uniform noise so
     * that the data resembles a noisy real-world signal.
     */
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
     * Measures how "rough" a smoothed series is by summing the squared
     * differences between consecutive points. A smoother curve varies less
     * from point to point, so it yields a smaller value.
     */
    private double roughness(double[] smoothed) {
        double sumOfSquaredSteps = 0;
        for (int j = 1; j < smoothed.length; ++j) {
            sumOfSquaredSteps += JdkMath.pow(smoothed[j] - smoothed[j - 1], 2);
        }
        return sumOfSquaredSteps;
    }

    @Test
    public void testIncreasingBandwidthIncreasesSmoothness() {
        double[] xval = new double[NUM_POINTS];
        double[] yval = new double[NUM_POINTS];
        generateSineData(xval, yval, X_NOISE, Y_NOISE);

        // Larger bandwidths average over more neighbours, so each successive
        // bandwidth should produce a smoother (less rough) curve.
        double[] increasingBandwidths = { 0.1, 0.5, 1.0 };
        double[] roughnessPerBandwidth = new double[increasingBandwidths.length];

        for (int i = 0; i < increasingBandwidths.length; i++) {
            LoessInterpolator interpolator =
                    new LoessInterpolator(increasingBandwidths[i], ROBUSTNESS_ITERS, ACCURACY);
            double[] smoothed = interpolator.smooth(xval, yval);
            roughnessPerBandwidth[i] = roughness(smoothed);
        }

        // Roughness must strictly decrease as the bandwidth grows.
        for (int i = 1; i < roughnessPerBandwidth.length; ++i) {
            Assert.assertTrue(roughnessPerBandwidth[i] < roughnessPerBandwidth[i - 1]);
        }
    }
}
