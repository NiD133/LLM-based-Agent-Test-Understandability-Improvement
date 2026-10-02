package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

/**
 * Verifies that LoessInterpolator rejects x/y arrays of different lengths.
 */
public class LoessInterpolatorTest_testUnequalSizeArguments {

    // x has 3 elements, y has 4 — lengths intentionally mismatch
    private static final double[] X_THREE_POINTS = {1, 2, 3};
    private static final double[] Y_FOUR_POINTS  = {1, 2, 3, 4};

    @Test(expected = DimensionMismatchException.class)
    public void testUnequalSizeArguments() {
        new LoessInterpolator().smooth(X_THREE_POINTS, Y_FOUR_POINTS);
    }
}
