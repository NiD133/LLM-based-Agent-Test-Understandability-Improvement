package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.junit.Test;

public class LoessInterpolatorTest_testNotAllFiniteReal5 {

    // x-coordinates for the interpolation input (monotonically increasing, as required by Loess)
    private static final double[] X_VALUES = { 3, 4, 5 };

    // y-values where the last entry is positive infinity — an invalid (non-finite) real number
    private static final double[] Y_VALUES_WITH_POSITIVE_INFINITY = { 1, 2, Double.POSITIVE_INFINITY };

    /**
     * Loess smoothing requires all y-values to be finite real numbers.
     * Providing Double.POSITIVE_INFINITY as a y-value must throw NotFiniteNumberException.
     */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal5() {
        new LoessInterpolator().smooth(X_VALUES, Y_VALUES_WITH_POSITIVE_INFINITY);
    }
}
