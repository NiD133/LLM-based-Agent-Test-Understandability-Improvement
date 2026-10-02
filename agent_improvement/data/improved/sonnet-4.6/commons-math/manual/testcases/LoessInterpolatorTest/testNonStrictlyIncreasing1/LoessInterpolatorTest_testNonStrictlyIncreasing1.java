package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NonMonotonicSequenceException;
import org.junit.Test;

/**
 * Tests that LoessInterpolator rejects x-values that are not strictly increasing.
 * Loess smoothing requires x-values to be in strictly ascending order; if they are
 * not, a NonMonotonicSequenceException should be thrown.
 */
public class LoessInterpolatorTest_testNonStrictlyIncreasing1 {

    /**
     * Verifies that smooth() throws NonMonotonicSequenceException when x-values
     * are not strictly increasing (here: 4, 3, 1, 2 contains a decrease from 4→3).
     */
    @Test(expected = NonMonotonicSequenceException.class)
    public void testNonStrictlyIncreasing1() {
        double[] xValuesNotStrictlyIncreasing = { 4, 3, 1, 2 };
        double[] yValues = { 3, 4, 5, 6 };

        new LoessInterpolator().smooth(xValuesNotStrictlyIncreasing, yValues);
    }
}
