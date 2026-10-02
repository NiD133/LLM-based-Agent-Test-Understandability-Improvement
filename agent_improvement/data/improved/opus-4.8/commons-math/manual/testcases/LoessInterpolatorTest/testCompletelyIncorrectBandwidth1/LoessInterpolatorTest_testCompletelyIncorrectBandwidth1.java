package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.OutOfRangeException;
import org.junit.Test;

public class LoessInterpolatorTest_testCompletelyIncorrectBandwidth1 {

    /**
     * The bandwidth passed to {@link LoessInterpolator} must lie within the
     * range [0, 1]. A negative bandwidth (-0.2) is therefore invalid and must
     * be rejected with an {@link OutOfRangeException}.
     */
    @Test(expected = OutOfRangeException.class)
    public void testCompletelyIncorrectBandwidth1() {
        final double invalidBandwidth = -0.2;
        final int robustnessIters = 3;
        final double accuracy = 1e-12;

        new LoessInterpolator(invalidBandwidth, robustnessIters, accuracy);
    }
}
