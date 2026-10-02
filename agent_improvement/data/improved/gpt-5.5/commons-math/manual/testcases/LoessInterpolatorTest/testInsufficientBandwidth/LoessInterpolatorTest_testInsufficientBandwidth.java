package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.NumberIsTooSmallException;
import org.junit.Test;

public class LoessInterpolatorTest_testInsufficientBandwidth {

    private static final double BANDWIDTH = 0.1;
    private static final int ROBUSTNESS_ITERATIONS = 3;
    private static final double ACCURACY = 1e-12;

    private static final double[] SAMPLE_X_VALUES = {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
    };

    private static final double[] SAMPLE_Y_VALUES = {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
    };

    @Test(expected = NumberIsTooSmallException.class)
    public void testInsufficientBandwidth() {
        LoessInterpolator interpolator = new LoessInterpolator(BANDWIDTH, ROBUSTNESS_ITERATIONS, ACCURACY);

        interpolator.smooth(SAMPLE_X_VALUES, SAMPLE_Y_VALUES);
    }
}
