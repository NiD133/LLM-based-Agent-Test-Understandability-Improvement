package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

public class LoessInterpolatorTest_testMath296withoutWeights {
    private static final double[] X_VALUES = {
        0.1, 0.2, 0.3, 0.4, 0.5,
        0.6, 0.7, 0.8, 0.9, 1.0,
        1.1, 1.2, 1.3, 1.4, 1.5,
        1.6, 1.7, 1.8, 1.9, 2.0
    };

    private static final double[] Y_VALUES = {
        0.47, 0.48, 0.55, 0.56, -0.08,
        -0.04, -0.07, -0.07, -0.56, -0.46,
        -0.56, -0.52, -3.03, -3.08, -3.09,
        -3.04, 3.54, 3.46, 3.36, 3.35
    };

    /** Output from R, rounded to .001. */
    private static final double[] EXPECTED_SMOOTHED_VALUES = {
        0.461, 0.499, 0.541, 0.308, 0.175,
        -0.042, -0.072, -0.196, -0.311, -0.446,
        -0.557, -1.497, -2.133, -3.08, -3.09,
        -0.621, 0.982, 3.449, 3.389, 3.336
    };

    private static final double BANDWIDTH = 0.3;
    private static final int ROBUSTNESS_ITERATIONS = 4;
    private static final double ACCURACY = 1e-12;
    private static final double ASSERTION_TOLERANCE = 0.02;

    @Test
    public void testMath296withoutWeights() {
        LoessInterpolator interpolator = new LoessInterpolator(
            BANDWIDTH,
            ROBUSTNESS_ITERATIONS,
            ACCURACY
        );

        double[] smoothedValues = interpolator.smooth(X_VALUES, Y_VALUES);

        Assert.assertEquals(X_VALUES.length, smoothedValues.length);
        for (int i = 0; i < smoothedValues.length; ++i) {
            Assert.assertEquals(EXPECTED_SMOOTHED_VALUES[i], smoothedValues[i], ASSERTION_TOLERANCE);
        }
    }
}
