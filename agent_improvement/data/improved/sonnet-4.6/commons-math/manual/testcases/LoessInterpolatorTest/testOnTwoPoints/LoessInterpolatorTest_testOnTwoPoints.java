package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests LoessInterpolator.smooth() when given the minimum viable input: exactly two data points.
 * LOESS smoothing with two points should pass both values through unchanged.
 */
public class LoessInterpolatorTest_testOnTwoPoints {

    @Test
    public void testOnTwoPoints() {
        // Two x-values (must be strictly increasing) and their corresponding y-values
        double[] xval = {0.5, 0.6};
        double[] yval = {0.7, 0.8};

        double[] smoothed = new LoessInterpolator().smooth(xval, yval);

        // With only two points, LOESS has no neighbors to average — the smoothed values
        // must equal the original y-values exactly (delta = 0.0 means exact equality).
        Assert.assertEquals("smoothed array should have the same length as input", 2, smoothed.length);
        Assert.assertEquals("first smoothed value should equal original y[0]",  0.7, smoothed[0], 0.0);
        Assert.assertEquals("second smoothed value should equal original y[1]", 0.8, smoothed[1], 0.0);
    }
}
