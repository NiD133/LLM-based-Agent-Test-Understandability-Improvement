package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.OutOfRangeException;
import org.junit.Test;

public class LoessInterpolatorTest_testCompletelyIncorrectBandwidth1 {

    private static final double NEGATIVE_BANDWIDTH = -0.2;
    private static final int ROBUSTNESS_ITERATIONS = 3;
    private static final double ACCURACY = 1e-12;

    @Test(expected = OutOfRangeException.class)
    public void testCompletelyIncorrectBandwidth1() {
        new LoessInterpolator(NEGATIVE_BANDWIDTH, ROBUSTNESS_ITERATIONS, ACCURACY);
    }
}
