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

    // ln(2) ≈ 0.693 is a valid bandwidth value (must be in (0, 1])
    private static final double BANDWIDTH = 0.6931470632553101;
    private static final int ROBUSTNESS_ITERATIONS = 176;
    private static final double EXPECTED_DEFAULT_BANDWIDTH = 0.3;
    private static final double DELTA = 0.01;

    @Test(timeout = 4000)
    public void test12_defaultBandwidthConstantIsApproximatelyPointThree() throws Throwable {
        // Construct an interpolator with a custom bandwidth and robustness iteration count
        // to confirm the class can be instantiated with these parameters
        LoessInterpolator interpolator = new LoessInterpolator(BANDWIDTH, ROBUSTNESS_ITERATIONS);

        // The DEFAULT_BANDWIDTH constant should always be approximately 0.3,
        // regardless of how individual instances are configured
        assertEquals(EXPECTED_DEFAULT_BANDWIDTH, LoessInterpolator.DEFAULT_BANDWIDTH, DELTA);
    }
}
