package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

public class LoessInterpolatorTest_testNotAllFiniteReal4 {

    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal4() {
        final double[] xValues = {3, 4, 5};
        final double[] yValuesWithNaN = {1, 2, Double.NaN};

        new LoessInterpolator().smooth(xValues, yValuesWithNaN);
    }
}
