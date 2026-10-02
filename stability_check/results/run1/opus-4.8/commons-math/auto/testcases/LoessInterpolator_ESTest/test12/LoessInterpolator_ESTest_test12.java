package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test12 extends LoessInterpolator_ESTest_scaffolding {

    /** Tolerance used when comparing floating-point constants. */
    private static final double COMPARISON_TOLERANCE = 0.01;

    /**
     * Verifies that constructing a LoessInterpolator with an explicit bandwidth
     * and robustness-iteration count leaves the class-level DEFAULT_BANDWIDTH
     * constant untouched (it should always report the documented value of 0.3).
     */
    @Test(timeout = 4000)
    public void constructingInterpolatorDoesNotChangeDefaultBandwidth() throws Throwable {
        double customBandwidth = 0.6931470632553101;
        int robustnessIterations = 176;

        new LoessInterpolator(customBandwidth, robustnessIterations);

        assertEquals(0.3, LoessInterpolator.DEFAULT_BANDWIDTH, COMPARISON_TOLERANCE);
    }
}
