package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that LOESS smoothing reduces noise on a distorted sine wave,
 * i.e., the smoothed curve is closer to the true sine than the raw noisy samples.
 */
public class LoessInterpolatorTest_testOnDistortedSine {

    // Number of sample points generated along one full period [0, 2π]
    private static final int NUM_POINTS = 100;

    // Fraction of points used in each local regression window (bandwidth)
    private static final double LOESS_BANDWIDTH = 0.3;

    // Number of robustness iterations for down-weighting outliers
    private static final int LOESS_ROBUSTNESS_ITERS = 4;

    // Convergence criterion for the robustness iterations
    private static final double LOESS_ACCURACY = 1e-12;

    // Maximum relative jitter applied to the x-spacing (10 % of the nominal step)
    private static final double X_NOISE_FRACTION = 0.1;

    // Maximum absolute noise added to each y-value (20 % of the unit amplitude)
    private static final double Y_NOISE_AMPLITUDE = 0.2;

    /**
     * Fills {@code xval} and {@code yval} with one period of a noisy sine wave.
     *
     * <p>X-coordinates are spaced roughly uniformly over [0, 2π] but each step
     * is jittered by up to {@code xnoise} relative to the nominal step size.
     * Y-values equal sin(x) plus uniform noise scaled by {@code ynoise}.</p>
     */
    private void generateNoisySineData(double[] xval, double[] yval,
                                       double xnoise, double ynoise) {
        double nominalStep = 2 * JdkMath.PI / xval.length;
        double x = 0;
        for (int i = 0; i < xval.length; ++i) {
            xval[i] = x;
            yval[i] = JdkMath.sin(x) + (2 * JdkMath.random() - 1) * ynoise;
            x += nominalStep * (1 + (2 * JdkMath.random() - 1) * xnoise);
        }
    }

    /**
     * Verifies that LOESS smoothing brings the curve closer to the true sine
     * than the raw noisy observations, measured by sum of squared residuals.
     */
    @Test
    public void testOnDistortedSine() {
        // --- Setup: generate a noisy, irregularly-spaced sine wave ---
        double[] xval = new double[NUM_POINTS];
        double[] yval = new double[NUM_POINTS];
        generateNoisySineData(xval, yval, X_NOISE_FRACTION, Y_NOISE_AMPLITUDE);

        // --- Act: smooth the noisy samples with LOESS ---
        LoessInterpolator interpolator = new LoessInterpolator(
                LOESS_BANDWIDTH, LOESS_ROBUSTNESS_ITERS, LOESS_ACCURACY);
        double[] smoothedValues = interpolator.smooth(xval, yval);

        // --- Assert: smoothed curve has lower total squared error than raw data ---
        // If LOESS is working correctly the fitted values should track the true
        // sine more closely than the noise-corrupted observations do.
        double noisySSE = 0;   // sum of squared errors for the raw noisy signal
        double smoothedSSE = 0; // sum of squared errors for the LOESS-smoothed signal
        for (int i = 0; i < NUM_POINTS; ++i) {
            double trueSineValue = JdkMath.sin(xval[i]);
            double noisyObservation = yval[i];
            double smoothedEstimate = smoothedValues[i];
            noisySSE   += JdkMath.pow(noisyObservation - trueSineValue, 2);
            smoothedSSE += JdkMath.pow(smoothedEstimate - trueSineValue, 2);
        }
        Assert.assertTrue(
                "LOESS-smoothed SSE should be less than noisy SSE",
                smoothedSSE < noisySSE);
    }
}
