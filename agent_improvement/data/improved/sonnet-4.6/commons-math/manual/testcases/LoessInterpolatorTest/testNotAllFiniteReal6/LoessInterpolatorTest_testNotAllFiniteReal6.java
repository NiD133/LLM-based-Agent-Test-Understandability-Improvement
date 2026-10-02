package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

/**
 * Verifies that {@link LoessInterpolator#smooth} rejects y-values that contain
 * non-finite numbers (NaN, positive infinity, or negative infinity).
 */
public class LoessInterpolatorTest_testNotAllFiniteReal6 {

    private static final double NEGATIVE_INFINITY_Y = Double.NEGATIVE_INFINITY;

    /**
     * Passing {@code Double.NEGATIVE_INFINITY} as one of the y-values must
     * cause {@code smooth()} to throw {@link NotFiniteNumberException} because
     * the LOESS algorithm requires all input values to be real, finite numbers.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal6() {
        double[] xValues = { 3, 4, 5 };
        double[] yValuesWithNegativeInfinity = { 1, 2, NEGATIVE_INFINITY_Y };

        new LoessInterpolator().smooth(xValues, yValuesWithNegativeInfinity);
    }
}
