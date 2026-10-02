package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test04 extends LoessInterpolator_ESTest_scaffolding {

    /**
     * Smoothing a two-point series where the same array serves as both the
     * x-coordinates and the y-coordinates. With points {0.0, 2.0} the loess
     * fit reproduces the original values within tolerance.
     */
    @Test(timeout = 4000)
    public void smoothTwoPointSeriesReturnsOriginalValues() throws Throwable {
        LoessInterpolator loessInterpolator = new LoessInterpolator();

        // Same array is passed for both x and y: points are {0.0, 2.0}.
        double[] points = new double[2];
        points[1] = 2.0;

        double[] smoothedValues = loessInterpolator.smooth(points, points);

        double[] expectedSmoothedValues = { 0.0, 2.0 };
        double tolerance = 0.01;
        assertArrayEquals(expectedSmoothedValues, smoothedValues, tolerance);
    }
}
