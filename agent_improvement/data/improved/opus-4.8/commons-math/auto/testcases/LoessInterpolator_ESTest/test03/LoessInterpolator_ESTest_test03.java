package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test03 extends LoessInterpolator_ESTest_scaffolding {

    /** Tolerance for comparing the smoothed values against the expected output. */
    private static final double SMOOTHING_TOLERANCE = 0.01;

    /**
     * Verifies that {@code smooth} produces the expected fitted values when the same
     * data set is used for both the abscissae and the ordinates.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Data points are strictly increasing in x (required by LoessInterpolator)
        // and the y-values mirror the x-values, since we pass the same array twice.
        double[] points = new double[4];
        points[0] = 0.0;
        points[1] = 0.3;
        points[2] = 2.0;
        points[3] = 3745.0491385;

        // bandwidth ~= 1.0 (uses essentially all points), 2 robustness iterations, accuracy = 1.
        double bandwidth = 0.9999999999999997;
        int robustnessIters = 2;
        double accuracy = 1;
        LoessInterpolator interpolator = new LoessInterpolator(bandwidth, robustnessIters, accuracy);

        double[] smoothed = interpolator.smooth(points, points);

        double[] expectedSmoothed = {
            0.14923934718433918,
            0.1512398973385424,
            1.907630988400478,
            3745.0491290577315
        };
        assertArrayEquals(expectedSmoothed, smoothed, SMOOTHING_TOLERANCE);
    }
}
