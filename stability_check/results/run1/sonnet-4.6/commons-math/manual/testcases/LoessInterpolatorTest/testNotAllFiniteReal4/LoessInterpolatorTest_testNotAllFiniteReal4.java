package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

/**
 * Verifies that {@link LoessInterpolator#smooth} rejects y-values that are not
 * finite (NaN or infinite), ensuring the interpolator fails fast on bad input.
 */
public class LoessInterpolatorTest_testNotAllFiniteReal4 {

    /**
     * Passing a NaN as one of the y-values must throw NotFiniteNumberException.
     * The x-values are valid and strictly increasing; only the last y-value is
     * non-finite, which is the condition under test.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal4() {
        double[] xValues = { 3, 4, 5 };
        double[] yValuesWithNaN = { 1, 2, Double.NaN };

        new LoessInterpolator().smooth(xValues, yValuesWithNaN);
    }
}
