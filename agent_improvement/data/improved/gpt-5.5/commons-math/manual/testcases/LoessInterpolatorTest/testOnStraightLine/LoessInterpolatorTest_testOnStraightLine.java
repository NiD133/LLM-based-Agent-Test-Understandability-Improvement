package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

public class LoessInterpolatorTest_testOnStraightLine {

    private static final double BANDWIDTH = 0.6;
    private static final int ROBUSTNESS_ITERATIONS = 2;
    private static final double ACCURACY = 1e-12;
    private static final double ASSERTION_TOLERANCE = 1e-8;

    @Test
    public void testOnStraightLine() {
        double[] xValues = { 1, 2, 3, 4, 5 };
        double[] yValues = { 2, 4, 6, 8, 10 };

        LoessInterpolator interpolator =
                new LoessInterpolator(BANDWIDTH, ROBUSTNESS_ITERATIONS, ACCURACY);
        double[] smoothedValues = interpolator.smooth(xValues, yValues);

        Assert.assertEquals(5, smoothedValues.length);
        for (int i = 0; i < 5; ++i) {
            Assert.assertEquals(yValues[i], smoothedValues[i], ASSERTION_TOLERANCE);
        }
    }
}
