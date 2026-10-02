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

public class LoessInterpolatorTest_testFitWithVaryingWeightsAndUnevenXSpacing {

    private void generateSineData(double[] xval, double[] yval, double xnoise, double ynoise) {
        double dx = 2 * JdkMath.PI / xval.length;
        double x = 0;
        for (int i = 0; i < xval.length; ++i) {
            xval[i] = x;
            yval[i] = JdkMath.sin(x) + (2 * JdkMath.random() - 1) * ynoise;
            x += dx * (1 + (2 * JdkMath.random() - 1) * xnoise);
        }
    }

    // MATH-1379
    @Test
    public void testFitWithVaryingWeightsAndUnevenXSpacing() {
        final double[] xval = { 0.1, 0.12, 0.23, 0.4, 0.57, 0.7, 0.87, 1.3, 1.9, 2.2, 2.3, 2.65, 3.0, 3.1, 3.5, 4.6, 4.7, 5.8, 5.95, 6.1 };
        final double[] yval = { 0.47, 0.48, 0.55, 0.56, -0.08, -0.04, -0.07, -0.07, -0.56, -0.46, -0.56, -0.52, -3.03, -3.08, -3.09, -3.04, 3.54, 3.46, 3.36, 3.35 };
        final double[] weights = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.8, 0.5, 0.5, 0.8, 0.8, 0.5, 0.5, 0.9, 1 };
        // Compare with output from R.
        // predict(loess(y ~ x, data.frame(x=xval, y=yval), weights, span=0.35, degree=1, family="symmetric", control=loess.control(iterations=1, surface="direct")))
        final double[] yref = { 0.556184894, 0.541907126, 0.455059334, 0.303681477, 0.142126445, 0.002615653, -0.031178445, -0.187124310, -0.406403569, -0.531957113, -0.669978426, -1.411039850, -2.225022609, -2.463874517, -3.298767758, -0.506116921, -0.231242991, 2.873755984, 3.295897106, 3.715495321 };
        final double delta = 1e-8;
        // Note R counts all iterations whereas LoessInterpolator robustness iterations are
        // in addition to the initial fit.
        final LoessInterpolator li = new LoessInterpolator(0.35, 0, 1e-12);
        final double[] res = li.smooth(xval, yval, weights);
        Assert.assertEquals(xval.length, res.length);
        for (int i = 0; i < res.length; ++i) {
            Assert.assertEquals(yref[i], res[i], delta);
        }
    }
}
