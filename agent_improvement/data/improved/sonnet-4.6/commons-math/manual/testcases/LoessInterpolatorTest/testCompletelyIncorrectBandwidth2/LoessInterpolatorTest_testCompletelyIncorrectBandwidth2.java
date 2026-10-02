package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.OutOfRangeException;
import org.junit.Test;

public class LoessInterpolatorTest_testCompletelyIncorrectBandwidth2 {

    // LoessInterpolator requires bandwidth in (0, 1]; a value > 1 must be rejected.
    @Test(expected = OutOfRangeException.class)
    public void testCompletelyIncorrectBandwidth2() {
        double bandwidthAboveMaximum = 1.1;
        new LoessInterpolator(bandwidthAboveMaximum, 3, 1e-12);
    }
}
