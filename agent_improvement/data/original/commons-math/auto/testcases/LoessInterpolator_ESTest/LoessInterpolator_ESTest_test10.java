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

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        LoessInterpolator loessInterpolator0 = null;
        try {
            loessInterpolator0 = new LoessInterpolator(2, 2, 1.0E-12);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // 2 out of [0, 1] range: bandwidth (2)
            //
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
