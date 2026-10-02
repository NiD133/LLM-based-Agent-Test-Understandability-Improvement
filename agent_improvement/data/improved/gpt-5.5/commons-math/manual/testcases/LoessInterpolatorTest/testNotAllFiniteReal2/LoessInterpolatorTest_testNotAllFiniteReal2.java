package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

public class LoessInterpolatorTest_testNotAllFiniteReal2 {

    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal2() {
        double[] xValues = {1, 2, Double.POSITIVE_INFINITY};
        double[] yValues = {3, 4, 5};

        new LoessInterpolator().smooth(xValues, yValues);
    }
}
