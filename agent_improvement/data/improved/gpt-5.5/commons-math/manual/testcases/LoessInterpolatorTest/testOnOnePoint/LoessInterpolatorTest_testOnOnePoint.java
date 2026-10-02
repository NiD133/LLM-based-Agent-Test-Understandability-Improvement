package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

public class LoessInterpolatorTest_testOnOnePoint {

    @Test
    public void testOnOnePoint() {
        double[] xValues = { 0.5 };
        double[] yValues = { 0.7 };

        double[] smoothedValues = new LoessInterpolator().smooth(xValues, yValues);

        Assert.assertEquals(1, smoothedValues.length);
        Assert.assertEquals(0.7, smoothedValues[0], 0.0);
    }
}
