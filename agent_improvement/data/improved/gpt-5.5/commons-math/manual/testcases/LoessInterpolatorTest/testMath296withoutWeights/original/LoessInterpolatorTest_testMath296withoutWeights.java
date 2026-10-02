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

public class LoessInterpolatorTest_testMath296withoutWeights {

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
    public void testMath296withoutWeights() {
        double[] xval = { 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0 };
        double[] yval = { 0.47, 0.48, 0.55, 0.56, -0.08, -0.04, -0.07, -0.07, -0.56, -0.46, -0.56, -0.52, -3.03, -3.08, -3.09, -3.04, 3.54, 3.46, 3.36, 3.35 };
        // Output from R, rounded to .001
        double[] yref = { 0.461, 0.499, 0.541, 0.308, 0.175, -0.042, -0.072, -0.196, -0.311, -0.446, -0.557, -1.497, -2.133, -3.08, -3.09, -0.621, 0.982, 3.449, 3.389, 3.336 };
        LoessInterpolator li = new LoessInterpolator(0.3, 4, 1e-12);
        double[] res = li.smooth(xval, yval);
        Assert.assertEquals(xval.length, res.length);
        for (int i = 0; i < res.length; ++i) {
            Assert.assertEquals(yref[i], res[i], 0.02);
        }
    }
}
