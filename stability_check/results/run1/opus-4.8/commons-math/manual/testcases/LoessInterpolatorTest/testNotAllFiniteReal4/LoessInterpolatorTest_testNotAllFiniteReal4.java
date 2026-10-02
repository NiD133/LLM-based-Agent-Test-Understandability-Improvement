package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

/**
 * Verifies that {@link LoessInterpolator#smooth(double[], double[])} rejects
 * input whose response values (y) are not all finite.
 */
public class LoessInterpolatorTest_testNotAllFiniteReal4 {

    /**
     * When a y-value is NaN, smoothing cannot be performed and the
     * interpolator must fail fast with a {@link NotFiniteNumberException}.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal4() {
        final double[] xValues = { 3, 4, 5 };
        final double[] yValuesWithNaN = { 1, 2, Double.NaN };

        new LoessInterpolator().smooth(xValues, yValuesWithNaN);
    }
}
