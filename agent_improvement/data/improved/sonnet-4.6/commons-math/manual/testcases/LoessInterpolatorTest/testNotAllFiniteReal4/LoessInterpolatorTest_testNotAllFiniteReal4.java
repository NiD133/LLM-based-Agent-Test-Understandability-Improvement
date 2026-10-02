package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

public class LoessInterpolatorTest_testNotAllFiniteReal4 {

    /**
     * LOESS smoothing requires all y-values to be finite real numbers.
     * Providing NaN as a y-value must throw NotFiniteNumberException.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal4() {
        double[] xValues = { 3, 4, 5 };
        double[] yValuesWithNaN = { 1, 2, Double.NaN };

        new LoessInterpolator().smooth(xValues, yValuesWithNaN);
    }
}
