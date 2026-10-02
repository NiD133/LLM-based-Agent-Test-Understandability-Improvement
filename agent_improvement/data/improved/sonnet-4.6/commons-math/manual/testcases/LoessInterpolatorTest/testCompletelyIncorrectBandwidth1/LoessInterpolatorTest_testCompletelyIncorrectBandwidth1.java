package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.OutOfRangeException;
import org.junit.Test;

/**
 * Tests that LoessInterpolator rejects a bandwidth value outside the valid [0,1] range.
 */
public class LoessInterpolatorTest_testCompletelyIncorrectBandwidth1 {

    // LoessInterpolator requires bandwidth in [0, 1]; negative values are invalid
    private static final double INVALID_NEGATIVE_BANDWIDTH = -0.2;
    private static final int ROBUSTNESS_ITERATIONS = 3;
    private static final double ACCURACY = 1e-12;

    @Test(expected = OutOfRangeException.class)
    public void testCompletelyIncorrectBandwidth1() {
        // A bandwidth of -0.2 is below the minimum of 0, so OutOfRangeException must be thrown
        new LoessInterpolator(INVALID_NEGATIVE_BANDWIDTH, ROBUSTNESS_ITERATIONS, ACCURACY);
    }
}
