package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test09 extends LoessInterpolator_ESTest_scaffolding {

    /**
     * The number of robustness iterations passed to the LoessInterpolator
     * constructor must not be negative. Constructing one with a negative value
     * (here -605) is expected to fail with a RuntimeException originating from
     * LoessInterpolator itself.
     */
    @Test(timeout = 4000)
    public void testConstructorRejectsNegativeRobustnessIterations() throws Throwable {
        double bandwidth = 0.0;
        int negativeRobustnessIters = -605;
        double accuracy = 0.0;

        try {
            new LoessInterpolator(bandwidth, negativeRobustnessIters, accuracy);
            fail("Expected a RuntimeException for negative robustness iterations (-605)");
        } catch (RuntimeException e) {
            // Exception must come from LoessInterpolator, which validates the
            // number of robustness iterations.
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
