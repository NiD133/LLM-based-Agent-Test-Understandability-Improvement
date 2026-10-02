package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

public class LoessInterpolatorTest_testOnTwoPoints {

    @Test
    public void testOnTwoPoints() {
        double[] xValues = { 0.5, 0.6 };
        double[] yValues = { 0.7, 0.8 };

        double[] smoothedValues = new LoessInterpolator().smooth(xValues, yValues);

        Assert.assertEquals(2, smoothedValues.length);
        Assert.assertEquals(0.7, smoothedValues[0], 0.0);
        Assert.assertEquals(0.8, smoothedValues[1], 0.0);
    }
}
