package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

/**
 * Verifies that {@link LoessInterpolator} rejects input data containing
 * non-finite values, such as {@code Double.NaN}.
 */
public class LoessInterpolatorTest_testNotAllFiniteReal1 {

    /**
     * Smoothing must fail when an abscissa is not a finite real number.
     * Here the third x-value is {@code Double.NaN}, so {@code smooth}
     * is expected to throw a {@link NotFiniteNumberException}.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal1() {
        final double[] xValuesWithNaN = { 1, 2, Double.NaN };
        final double[] yValues = { 3, 4, 5 };

        new LoessInterpolator().smooth(xValuesWithNaN, yValues);
    }
}
