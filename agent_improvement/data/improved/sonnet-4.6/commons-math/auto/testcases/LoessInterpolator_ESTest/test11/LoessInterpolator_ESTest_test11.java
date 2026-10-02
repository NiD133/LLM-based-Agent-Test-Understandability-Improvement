package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test11 extends LoessInterpolator_ESTest_scaffolding {

    /**
     * Verifies that constructing a LoessInterpolator with a bandwidth value outside
     * the valid [0, 1] range throws a RuntimeException.
     *
     * The LoessInterpolator(double bandwidth, int robustnessIters, double accuracy)
     * constructor requires bandwidth in [0, 1]. Passing -1461 (far below 0) should
     * trigger an immediate validation failure.
     */
    @Test(timeout = 4000)
    public void test_constructorRejectsNegativeBandwidth() throws Throwable {
        final double invalidBandwidth = -1461;
        final int robustnessIters = -1461;
        final double accuracy = -1461;

        try {
            new LoessInterpolator(invalidBandwidth, robustnessIters, accuracy);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Expected: bandwidth -1,461 is outside the required [0, 1] range
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
