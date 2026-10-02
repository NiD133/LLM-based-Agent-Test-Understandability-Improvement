package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

public class LoessInterpolatorTest_testNotAllFiniteReal3 {

    /**
     * smooth() must reject input whose abscissa (x) values are not all finite.
     * Here the third x value is negative infinity, so a
     * {@link NotFiniteNumberException} is expected.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal3() {
        final double[] xValuesWithInfinity = { 1, 2, Double.NEGATIVE_INFINITY };
        final double[] yValues = { 3, 4, 5 };

        new LoessInterpolator().smooth(xValuesWithInfinity, yValues);
    }
}
