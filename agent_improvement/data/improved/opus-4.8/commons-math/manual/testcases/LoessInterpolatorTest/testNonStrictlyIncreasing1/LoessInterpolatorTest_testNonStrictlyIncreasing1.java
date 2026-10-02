package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NonMonotonicSequenceException;
import org.junit.Test;

/**
 * Verifies that {@link LoessInterpolator#smooth(double[], double[])} rejects
 * abscissae (x values) that are not strictly increasing.
 */
public class LoessInterpolatorTest_testNonStrictlyIncreasing1 {

    /**
     * The x array {4, 3, 1, 2} is not sorted in increasing order, so smoothing
     * must fail with a {@link NonMonotonicSequenceException}.
     */
    @Test(expected = NonMonotonicSequenceException.class)
    public void testNonStrictlyIncreasing1() {
        final double[] nonIncreasingX = { 4, 3, 1, 2 };
        final double[] y = { 3, 4, 5, 6 };

        new LoessInterpolator().smooth(nonIncreasingX, y);
    }
}
