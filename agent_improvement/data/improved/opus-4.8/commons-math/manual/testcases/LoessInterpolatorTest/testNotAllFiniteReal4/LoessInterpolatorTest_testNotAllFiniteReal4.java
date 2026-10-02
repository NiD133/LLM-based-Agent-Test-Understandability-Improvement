package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

/**
 * Verifies that {@link LoessInterpolator#smooth(double[], double[])} rejects
 * input that contains a non-finite value.
 */
public class LoessInterpolatorTest_testNotAllFiniteReal4 {

    /**
     * Smoothing must fail when one of the y-values is NaN: every observation
     * has to be a finite real number, so a {@link NotFiniteNumberException}
     * is expected.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal4() {
        final double[] xValues = { 3, 4, 5 };
        final double[] yValuesWithNaN = { 1, 2, Double.NaN };

        new LoessInterpolator().smooth(xValues, yValuesWithNaN);
    }
}
