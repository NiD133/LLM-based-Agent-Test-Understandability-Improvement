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
        int numPoints = 100;
        double[] xval = new double[numPoints];
        double[] yval = new double[numPoints];
        double xnoise = 0.1;
        double ynoise = 0.1;
        generateSineData(xval, yval, xnoise, ynoise);
        // Check that variance decreases as bandwidth increases
        double[] bandwidths = { 0.1, 0.5, 1.0 };
        double[] variances = new double[bandwidths.length];
        for (int i = 0; i < bandwidths.length; i++) {
            double bw = bandwidths[i];
            LoessInterpolator li = new LoessInterpolator(bw, 4, 1e-12);
            double[] res = li.smooth(xval, yval);
            for (int j = 1; j < res.length; ++j) {
                variances[i] += JdkMath.pow(res[j] - res[j - 1], 2);
            }
        }
        for (int i = 1; i < variances.length; ++i) {
            Assert.assertTrue(variances[i] < variances[i - 1]);
        }
    }
}
