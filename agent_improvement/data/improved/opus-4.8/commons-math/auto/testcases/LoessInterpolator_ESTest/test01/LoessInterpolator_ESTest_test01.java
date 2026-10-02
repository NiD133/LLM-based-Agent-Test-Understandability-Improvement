package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test01 extends LoessInterpolator_ESTest_scaffolding {

    /**
     * Smoothing with the same array used as both abscissae and point weights
     * produces a fitted series matching the expected loess output.
     */
    @Test(timeout = 4000)
    public void smoothReturnsExpectedFittedValues() throws Throwable {
        // The x-coordinates, also reused as the per-point weights below.
        double[] xValuesAndWeights = { 0.0, 0.3, 2.0, 3745.0491385 };
        // The y-values to be smoothed.
        double[] yValues = { 0.0, 0.0, 1.0, 0.0 };

        // bandwidth = 1.0, robustnessIters = 2, accuracy = -1094.0
        LoessInterpolator loessInterpolator = new LoessInterpolator(1.0, 2, -1094.0);

        double[] smoothed =
            loessInterpolator.smooth(xValuesAndWeights, yValues, xValuesAndWeights);

        double[] expectedSmoothed = { -0.1764705882352937, 7.216449660063518E-16, 1.0, 0.0 };
        assertArrayEquals(expectedSmoothed, smoothed, 0.01);
    }
}
