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
 * Verifies that {@link LoessInterpolator} reduces the noise present in a
 * jittered sine signal: the smoothed curve should fit the underlying "true"
 * sine more closely than the noisy samples do.
 */
public class LoessInterpolatorTest_testOnDistortedSine {

    /**
     * Fills {@code xval} and {@code yval} with a noisy sine wave sampled over
     * one full period [0, 2*PI).
     *
     * <p>The y-values are {@code sin(x)} perturbed by uniform noise in
     * {@code [-ynoise, ynoise]}. The x-spacing is a nominal step jittered by
     * up to {@code xnoise} (as a fraction of the step), which keeps the points
     * monotonically increasing while irregularly spaced.
     */
    private void generateSineData(double[] xval, double[] yval, double xnoise, double ynoise) {
        final double nominalStep = 2 * JdkMath.PI / xval.length;
        double x = 0;
        for (int i = 0; i < xval.length; ++i) {
            xval[i] = x;
            yval[i] = JdkMath.sin(x) + randomInRange(ynoise);
            x += nominalStep * (1 + randomInRange(xnoise));
        }
    }

    /** Returns a uniform random value in {@code [-amplitude, amplitude]}. */
    private double randomInRange(double amplitude) {
        return (2 * JdkMath.random() - 1) * amplitude;
    }

    @Test
    public void testOnDistortedSine() {
        final int numPoints = 100;
        final double xNoise = 0.1;
        final double yNoise = 0.2;

        final double[] xval = new double[numPoints];
        final double[] yval = new double[numPoints];
        generateSineData(xval, yval, xNoise, yNoise);

        // bandwidth = 0.3, robustnessIters = 4, accuracy = 1e-12
        final LoessInterpolator interpolator = new LoessInterpolator(0.3, 4, 1e-12);
        final double[] smoothed = interpolator.smooth(xval, yval);

        // Compare how far each signal is from the true sine, using the sum of
        // squared residuals. The smoothed fit should be closer than the noise.
        double noisyResidualSum = 0;
        double fitResidualSum = 0;
        for (int i = 0; i < numPoints; ++i) {
            final double trueSine = JdkMath.sin(xval[i]);
            noisyResidualSum += JdkMath.pow(yval[i] - trueSine, 2);
            fitResidualSum += JdkMath.pow(smoothed[i] - trueSine, 2);
        }

        Assert.assertTrue("Smoothed curve should fit the true sine better than the noisy samples",
                fitResidualSum < noisyResidualSum);
    }
}
