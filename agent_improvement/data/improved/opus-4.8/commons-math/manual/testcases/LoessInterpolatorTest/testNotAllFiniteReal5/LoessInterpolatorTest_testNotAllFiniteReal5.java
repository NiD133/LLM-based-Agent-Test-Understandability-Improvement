package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

/**
 * Verifies that {@link LoessInterpolator#smooth(double[], double[])} rejects input
 * whose y-values are not all finite.
 */
public class LoessInterpolatorTest_testNotAllFiniteReal5 {

    /**
     * The last y-value is positive infinity, so smoothing must fail with a
     * {@link NotFiniteNumberException} rather than producing a result.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal5() {
        final double[] xValues = { 3, 4, 5 };
        final double[] yValues = { 1, 2, Double.POSITIVE_INFINITY };

        new LoessInterpolator().smooth(xValues, yValues);
    }
}
