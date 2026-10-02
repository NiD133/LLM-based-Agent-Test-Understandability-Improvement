package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

public class LoessInterpolatorTest_testNotAllFiniteReal6 {

    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal6() {
        new LoessInterpolator().smooth(
                new double[] { 3, 4, 5 },
                new double[] { 1, 2, Double.NEGATIVE_INFINITY });
    }
}
