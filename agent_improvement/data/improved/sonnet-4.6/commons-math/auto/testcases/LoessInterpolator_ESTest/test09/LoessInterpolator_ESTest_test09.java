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
     * A negative number of robustness iterations is invalid and should cause a RuntimeException.
     */
    @Test(timeout = 4000)
    public void test09_negativeRobustnessIterationsThrowsException() throws Throwable {
        double bandwidth = 0.0;
        int negativeRobustnessIterations = -605;
        double accuracy = 0.0;

        try {
            new LoessInterpolator(bandwidth, negativeRobustnessIterations, accuracy);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
