package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

public class LoessInterpolatorTest_testNotAllFiniteReal3 {

    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal3() {
        // NEGATIVE_INFINITY in the x-values array is not a finite number,
        // so smooth() must reject it with NotFiniteNumberException.
        double[] xValues = { 1, 2, Double.NEGATIVE_INFINITY };
        double[] yValues = { 3, 4, 5 };
        new LoessInterpolator().smooth(xValues, yValues);
    }
}
