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
     * The constructor requires the bandwidth to lie within the [0, 1] range.
     * Here every argument is -1461, so the out-of-range bandwidth must be
     * rejected with a RuntimeException originating from LoessInterpolator.
     */
    @Test(timeout = 4000)
    public void constructorRejectsBandwidthOutsideUnitRange() throws Throwable {
        final double invalidBandwidth = -1461;
        final int robustnessIters = -1461;
        final double accuracy = -1461;

        try {
            new LoessInterpolator(invalidBandwidth, robustnessIters, accuracy);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Message: "-1,461 out of [0, 1] range: bandwidth (-1,461)"
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
