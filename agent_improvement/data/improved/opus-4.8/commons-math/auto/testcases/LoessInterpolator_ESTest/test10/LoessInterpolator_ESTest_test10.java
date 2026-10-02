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

    /**
     * The LoessInterpolator constructor requires the bandwidth to lie within the
     * [0, 1] range. Constructing it with a bandwidth of 2 must therefore be
     * rejected with a RuntimeException reporting the out-of-range value.
     */
    @Test(timeout = 4000)
    public void constructorRejectsBandwidthAboveOne() throws Throwable {
        double outOfRangeBandwidth = 2;
        int robustnessIterations = 2;
        double accuracy = 1.0E-12;

        try {
            new LoessInterpolator(outOfRangeBandwidth, robustnessIterations, accuracy);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Message: "2 out of [0, 1] range: bandwidth (2)"
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
