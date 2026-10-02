package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

/**
 * Verifies that {@link LoessInterpolator#smooth(double[], double[])} rejects
 * inputs whose abscissa and ordinate arrays have different lengths.
 */
public class LoessInterpolatorTest_testUnequalSizeArguments {

    /**
     * Smoothing requires one y-value per x-value. Supplying an x-array of
     * length 3 together with a y-array of length 4 must raise a
     * {@link DimensionMismatchException}.
     */
    @Test(expected = DimensionMismatchException.class)
    public void testUnequalSizeArguments() {
        final double[] xValues = { 1, 2, 3 };
        final double[] yValues = { 1, 2, 3, 4 };

        new LoessInterpolator().smooth(xValues, yValues);
    }
}
