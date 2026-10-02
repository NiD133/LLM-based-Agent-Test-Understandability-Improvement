package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

/**
 * Verifies that {@link LoessInterpolator#smooth(double[], double[])} rejects
 * input containing non-finite values.
 */
public class LoessInterpolatorTest_testNotAllFiniteReal4 {

    /**
     * Smoothing must fail when a y-value is not finite (here {@code Double.NaN}),
     * signalling the problem with a {@link NotFiniteNumberException}.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal4() {
        final double[] xValues = { 3, 4, 5 };
        final double[] yValuesWithNaN = { 1, 2, Double.NaN };

        new LoessInterpolator().smooth(xValues, yValuesWithNaN);
    }
}
