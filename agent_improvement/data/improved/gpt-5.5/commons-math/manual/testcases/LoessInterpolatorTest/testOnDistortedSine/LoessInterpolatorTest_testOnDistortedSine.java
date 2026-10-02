package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class LoessInterpolatorTest_testOnDistortedSine {

    private static final int NUMBER_OF_POINTS = 100;
    private static final double X_NOISE = 0.1;
    private static final double Y_NOISE = 0.2;

    private static final double BANDWIDTH = 0.3;
    private static final int ROBUSTNESS_ITERATIONS = 4;
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
    public void testOnDistortedSine() {
        double[] xval = new double[NUMBER_OF_POINTS];
        double[] yval = new double[NUMBER_OF_POINTS];
        generateSineData(xval, yval, X_NOISE, Y_NOISE);

        LoessInterpolator interpolator =
                new LoessInterpolator(BANDWIDTH, ROBUSTNESS_ITERATIONS, ACCURACY);
        double[] smoothedValues = interpolator.smooth(xval, yval);

        double noisyResidualSum = residualSumAgainstSine(xval, yval);
        double fitResidualSum = residualSumAgainstSine(xval, smoothedValues);
        Assert.assertTrue(fitResidualSum < noisyResidualSum);
    }

    private double residualSumAgainstSine(double[] xval, double[] yval) {
        double residualSum = 0;
        for (int i = 0; i < NUMBER_OF_POINTS; ++i) {
            double expected = JdkMath.sin(xval[i]);
            double actual = yval[i];
            residualSum += JdkMath.pow(actual - expected, 2);
        }
        return residualSum;
    }
}
