package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.OutOfRangeException;
import org.junit.Test;

public class LoessInterpolatorTest_testCompletelyIncorrectBandwidth2 {

    /**
     * The bandwidth must lie within the interval [0, 1]. Constructing a
     * {@link LoessInterpolator} with a bandwidth greater than 1 (here 1.1)
     * must therefore be rejected with an {@link OutOfRangeException}.
     */
    @Test(expected = OutOfRangeException.class)
    public void testCompletelyIncorrectBandwidth2() {
        final double bandwidthAboveOne = 1.1;
        final int robustnessIters = 3;
        final double accuracy = 1e-12;

        new LoessInterpolator(bandwidthAboveOne, robustnessIters, accuracy);
    }
}
