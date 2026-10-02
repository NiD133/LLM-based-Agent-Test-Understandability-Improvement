package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

public class LoessInterpolatorTest_testNotAllFiniteReal4 {

    /**
     * Smoothing should reject y-values that contain NaN, because LOESS requires
     * all data points to be finite real numbers.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal4() {
        double[] xValues = { 3, 4, 5 };
        double[] yValuesWithNaN = { 1, 2, Double.NaN };

        new LoessInterpolator().smooth(xValues, yValuesWithNaN);
    }
}
