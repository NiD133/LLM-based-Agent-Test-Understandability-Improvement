package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

public class LoessInterpolatorTest_testNotAllFiniteReal1 {

    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal1() {
        new LoessInterpolator().smooth(
                new double[] { 1, 2, Double.NaN },
                new double[] { 3, 4, 5 });
    }
}
