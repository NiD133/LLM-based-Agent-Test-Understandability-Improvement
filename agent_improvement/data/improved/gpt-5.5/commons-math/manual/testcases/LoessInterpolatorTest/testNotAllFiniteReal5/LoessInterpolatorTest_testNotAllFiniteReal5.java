package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

public class LoessInterpolatorTest_testNotAllFiniteReal5 {

    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal5() {
        double[] xValues = { 3, 4, 5 };
        double[] yValuesWithPositiveInfinity = { 1, 2, Double.POSITIVE_INFINITY };

        new LoessInterpolator().smooth(xValues, yValuesWithPositiveInfinity);
    }
}
