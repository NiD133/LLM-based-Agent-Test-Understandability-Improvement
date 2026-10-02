package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

/**
 * Verifies that {@link LoessInterpolator#smooth(double[], double[])} rejects
 * input whose y-values are not all finite.
 */
public class LoessInterpolatorTest_testNotAllFiniteReal6 {

    /**
     * When a y-value is non-finite (here, negative infinity), smoothing must
     * fail fast with a {@link NotFiniteNumberException}.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal6() {
        final double[] xValues = { 3, 4, 5 };
        final double[] yValues = { 1, 2, Double.NEGATIVE_INFINITY };

        new LoessInterpolator().smooth(xValues, yValues);
    }
}
