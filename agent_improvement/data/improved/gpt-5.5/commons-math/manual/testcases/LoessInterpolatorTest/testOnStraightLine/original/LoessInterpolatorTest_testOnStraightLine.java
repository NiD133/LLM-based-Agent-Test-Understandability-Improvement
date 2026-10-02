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

public class LoessInterpolatorTest_testOnStraightLine {

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
    public void testOnStraightLine() {
        double[] xval = { 1, 2, 3, 4, 5 };
        double[] yval = { 2, 4, 6, 8, 10 };
        LoessInterpolator li = new LoessInterpolator(0.6, 2, 1e-12);
        double[] res = li.smooth(xval, yval);
        Assert.assertEquals(5, res.length);
        for (int i = 0; i < 5; ++i) {
            Assert.assertEquals(yval[i], res[i], 1e-8);
        }
    }
}
