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

    // Custom bandwidth and iteration count used to construct the interpolator under test
    private static final double CUSTOM_BANDWIDTH = 0.6931470632553101;
    private static final int CUSTOM_ITERATIONS   = 176;

    // Tolerance for floating-point comparison of the expected default bandwidth
    private static final double BANDWIDTH_DELTA = 0.01;

    @Test(timeout = 4000)
    public void test_defaultBandwidthConstantEqualsPointThree() throws Throwable {
        // Construct a LoessInterpolator with a non-default bandwidth and iteration count
        // to verify that the DEFAULT_BANDWIDTH class constant is unaffected by instance creation.
        LoessInterpolator interpolator = new LoessInterpolator(CUSTOM_BANDWIDTH, CUSTOM_ITERATIONS);

        assertEquals(0.3, LoessInterpolator.DEFAULT_BANDWIDTH, BANDWIDTH_DELTA);
    }
}
