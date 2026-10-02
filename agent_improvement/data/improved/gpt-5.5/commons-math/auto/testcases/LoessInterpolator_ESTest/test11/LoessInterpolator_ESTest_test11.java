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

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        int invalidBandwidth = -1461;
        int invalidRobustnessIterations = -1461;
        double invalidAccuracy = -1461.0;

        try {
            new LoessInterpolator(invalidBandwidth, invalidRobustnessIterations, invalidAccuracy);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // -1,461 out of [0, 1] range: bandwidth (-1,461)
            //
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
