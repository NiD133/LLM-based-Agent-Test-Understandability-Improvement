package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

public class LoessInterpolatorTest_testNotAllFiniteReal1 {

    /**
     * Verifies that smooth() rejects x-values containing NaN by throwing
     * NotFiniteNumberException.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal1() {
        double[] xWithNaN = {1, 2, Double.NaN};
        double[] yValues  = {3, 4, 5};
        new LoessInterpolator().smooth(xWithNaN, yValues);
    }
}
