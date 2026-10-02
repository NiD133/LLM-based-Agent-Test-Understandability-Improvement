package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test10 extends LoessInterpolator_ESTest_scaffolding {

    // LoessInterpolator requires bandwidth in [0, 1]; a value of 2 is out of range and should throw.
    private static final double BANDWIDTH_OUT_OF_RANGE = 2.0;
    private static final int ROBUSTNESS_ITERS = 2;
    private static final double ACCURACY = 1.0E-12;

    @Test(timeout = 4000)
    public void test_constructorThrowsWhenBandwidthExceedsOne() throws Throwable {
        try {
            new LoessInterpolator(BANDWIDTH_OUT_OF_RANGE, ROBUSTNESS_ITERS, ACCURACY);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // bandwidth=2 is outside the valid [0,1] range, so construction must fail
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
