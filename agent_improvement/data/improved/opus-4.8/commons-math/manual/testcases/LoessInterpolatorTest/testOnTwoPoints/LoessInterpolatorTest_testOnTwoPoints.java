package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies how {@link LoessInterpolator} behaves on the smallest possible data
 * set: exactly two points. With only two points there is nothing to smooth, so
 * the interpolator is expected to return the original y-values unchanged.
 */
public class LoessInterpolatorTest_testOnTwoPoints {

    @Test
    public void testOnTwoPoints() {
        // A data set with the minimum number of points the smoother accepts.
        double[] xValues = { 0.5, 0.6 };
        double[] yValues = { 0.7, 0.8 };

        double[] smoothed = new LoessInterpolator().smooth(xValues, yValues);

        // The output keeps the same length as the input.
        Assert.assertEquals(2, smoothed.length);
        // With only two points the y-values are returned exactly (zero tolerance).
        Assert.assertEquals(0.7, smoothed[0], 0.0);
        Assert.assertEquals(0.8, smoothed[1], 0.0);
    }
}
