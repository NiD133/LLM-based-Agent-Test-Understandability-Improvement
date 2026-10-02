package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

public class LoessInterpolatorTest_testOnOnePoint {

    /**
     * When smoothing a single data point, LOESS should return that exact value
     * unchanged — there is nothing to smooth against, so the output is a
     * one-element array equal to the input y-value.
     */
    @Test
    public void testOnOnePoint() {
        double inputX = 0.5;
        double inputY = 0.7;
        double[] xval = { inputX };
        double[] yval = { inputY };

        double[] smoothed = new LoessInterpolator().smooth(xval, yval);

        Assert.assertEquals("Smoothing a single point must return a one-element array",
                1, smoothed.length);
        Assert.assertEquals("Smoothed value must equal the original y-value when there is only one point",
                inputY, smoothed[0], 0.0);
    }
}
