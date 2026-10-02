package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies the degenerate case where {@link LoessInterpolator} is asked to
 * smooth a data set that contains a single point.
 */
public class LoessInterpolatorTest_testOnOnePoint {

    /**
     * Smoothing a single-point series must leave that point untouched: the
     * result should have exactly one value, equal to the original y-value.
     */
    @Test
    public void testOnOnePoint() {
        final double[] xValues = { 0.5 };
        final double[] yValues = { 0.7 };

        final double[] smoothed = new LoessInterpolator().smooth(xValues, yValues);

        Assert.assertEquals(1, smoothed.length);
        Assert.assertEquals(0.7, smoothed[0], 0.0);
    }
}
