package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

public class LoessInterpolatorTest_testNotAllFiniteReal2 {

    /**
     * Smoothing must reject input that is not entirely finite. Here the x
     * values contain a positive infinity, so the interpolator is expected to
     * throw a {@link NotFiniteNumberException}.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void smoothRejectsInfiniteXValue() {
        double[] xValuesWithInfinity = { 1, 2, Double.POSITIVE_INFINITY };
        double[] yValues = { 3, 4, 5 };

        new LoessInterpolator().smooth(xValuesWithInfinity, yValues);
    }
}
