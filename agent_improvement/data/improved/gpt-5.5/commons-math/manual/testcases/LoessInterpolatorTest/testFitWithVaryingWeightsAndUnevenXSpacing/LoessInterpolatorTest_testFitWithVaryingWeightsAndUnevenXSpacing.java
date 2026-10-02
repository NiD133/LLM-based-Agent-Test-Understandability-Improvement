package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

public class LoessInterpolatorTest_testFitWithVaryingWeightsAndUnevenXSpacing {

    private static final double BANDWIDTH = 0.35;
    private static final int ROBUSTNESS_ITERATIONS = 0;
    private static final double ACCURACY = 1e-12;
    private static final double ASSERTION_TOLERANCE = 1e-8;

    private static final double[] UNEVEN_X_VALUES = {
        0.1, 0.12, 0.23, 0.4, 0.57,
        0.7, 0.87, 1.3, 1.9, 2.2,
        2.3, 2.65, 3.0, 3.1, 3.5,
        4.6, 4.7, 5.8, 5.95, 6.1
    };

    private static final double[] OBSERVED_Y_VALUES = {
        0.47, 0.48, 0.55, 0.56, -0.08,
        -0.04, -0.07, -0.07, -0.56, -0.46,
        -0.56, -0.52, -3.03, -3.08, -3.09,
        -3.04, 3.54, 3.46, 3.36, 3.35
    };

    private static final double[] OBSERVATION_WEIGHTS = {
        1, 1, 1, 1, 1,
        1, 1, 1, 1, 1,
        1, 0.8, 0.5, 0.5, 0.8,
        0.8, 0.5, 0.5, 0.9, 1
    };

    // Output from R:
    // predict(loess(y ~ x, data.frame(x=xval, y=yval), weights, span=0.35,
    //        degree=1, family="symmetric",
    //        control=loess.control(iterations=1, surface="direct")))
    private static final double[] EXPECTED_SMOOTHED_VALUES = {
        0.556184894, 0.541907126, 0.455059334, 0.303681477, 0.142126445,
        0.002615653, -0.031178445, -0.187124310, -0.406403569, -0.531957113,
        -0.669978426, -1.411039850, -2.225022609, -2.463874517, -3.298767758,
        -0.506116921, -0.231242991, 2.873755984, 3.295897106, 3.715495321
    };

    // MATH-1379
    @Test
    public void testFitWithVaryingWeightsAndUnevenXSpacing() {
        // R counts all iterations, whereas LoessInterpolator robustness iterations
        // are in addition to the initial fit.
        final LoessInterpolator interpolator =
                new LoessInterpolator(BANDWIDTH, ROBUSTNESS_ITERATIONS, ACCURACY);

        final double[] smoothedValues =
                interpolator.smooth(UNEVEN_X_VALUES, OBSERVED_Y_VALUES, OBSERVATION_WEIGHTS);

        assertSmoothedValuesMatchRReference(smoothedValues);
    }

    private static void assertSmoothedValuesMatchRReference(final double[] actualValues) {
        Assert.assertEquals(UNEVEN_X_VALUES.length, actualValues.length);
        for (int i = 0; i < actualValues.length; ++i) {
            Assert.assertEquals(EXPECTED_SMOOTHED_VALUES[i], actualValues[i], ASSERTION_TOLERANCE);
        }
    }
}
