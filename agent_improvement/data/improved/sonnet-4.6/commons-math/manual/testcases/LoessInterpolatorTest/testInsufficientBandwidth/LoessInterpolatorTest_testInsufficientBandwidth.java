package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NumberIsTooSmallException;
import org.junit.Test;

public class LoessInterpolatorTest_testInsufficientBandwidth {

    // A bandwidth of 0.1 means only 10% of the 12 data points (1.2 points) are
    // used as neighbors — fewer than the minimum of 2 required by the algorithm.
    private static final double INSUFFICIENT_BANDWIDTH = 0.1;
    private static final int ROBUSTNESS_ITERS = 3;
    private static final double ACCURACY = 1e-12;

    private static final double[] XVAL = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
    private static final double[] YVAL = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

    @Test(expected = NumberIsTooSmallException.class)
    public void testInsufficientBandwidth() {
        LoessInterpolator li = new LoessInterpolator(INSUFFICIENT_BANDWIDTH, ROBUSTNESS_ITERS, ACCURACY);
        li.smooth(XVAL, YVAL);
    }
}
