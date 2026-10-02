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

public class LoessInterpolatorTest_testOnDistortedSine {

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
        int numPoints = 100;
        double[] xval = new double[numPoints];
        double[] yval = new double[numPoints];
        double xnoise = 0.1;
        double ynoise = 0.2;
        generateSineData(xval, yval, xnoise, ynoise);
        LoessInterpolator li = new LoessInterpolator(0.3, 4, 1e-12);
        double[] res = li.smooth(xval, yval);
        // Check that the resulting curve differs from
        // the "real" sine less than the jittered one
        double noisyResidualSum = 0;
        double fitResidualSum = 0;
        for (int i = 0; i < numPoints; ++i) {
            double expected = JdkMath.sin(xval[i]);
            double noisy = yval[i];
            double fit = res[i];
            noisyResidualSum += JdkMath.pow(noisy - expected, 2);
            fitResidualSum += JdkMath.pow(fit - expected, 2);
        }
        Assert.assertTrue(fitResidualSum < noisyResidualSum);
    }
}
