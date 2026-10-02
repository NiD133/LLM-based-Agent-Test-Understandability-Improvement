package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NonMonotonicSequenceException;
import org.junit.Test;

/**
 * Tests that LoessInterpolator rejects x-values that are not strictly increasing.
 * LOESS requires x-values to be strictly monotonically increasing; duplicate
 * values must cause a NonMonotonicSequenceException to be thrown.
 */
public class LoessInterpolatorTest_testNonStrictlyIncreasing2 {

    @Test(expected = NonMonotonicSequenceException.class)
    public void testNonStrictlyIncreasing2() {
        // x-values contain a duplicate (2 appears twice), violating strict monotonicity
        double[] xWithDuplicate = {1, 2, 2, 3};
        double[] yValues       = {3, 4, 5, 6};

        new LoessInterpolator().smooth(xWithDuplicate, yValues);
    }
}
