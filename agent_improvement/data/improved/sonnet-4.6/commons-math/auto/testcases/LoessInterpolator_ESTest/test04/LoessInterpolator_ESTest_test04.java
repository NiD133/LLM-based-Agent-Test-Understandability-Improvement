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

    private static final double COMPARISON_DELTA = 0.01;

    /**
     * Verifies that smooth() on a two-point dataset returns the endpoints unchanged.
     *
     * The dataset has x = [0.0, 2.0] and y = [0.0, 2.0] (the same array is passed
     * for both arguments). With only two data points, LOESS smoothing must interpolate
     * through both endpoints exactly, so the output should equal the input y values.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        LoessInterpolator loessInterpolator = new LoessInterpolator();

        // Build a two-element array representing both x-coordinates and y-values:
        // index 0: 0.0 (default), index 1: 2.0
        double[] xAndY = new double[2];
        xAndY[1] = 2.0;   // xAndY = [0.0, 2.0]

        // Pass the same array for x and y; smooth() must return [0.0, 2.0]
        double[] smoothed = loessInterpolator.smooth(xAndY, xAndY);

        assertArrayEquals(new double[] { 0.0, 2.0 }, smoothed, COMPARISON_DELTA);
    }
}
