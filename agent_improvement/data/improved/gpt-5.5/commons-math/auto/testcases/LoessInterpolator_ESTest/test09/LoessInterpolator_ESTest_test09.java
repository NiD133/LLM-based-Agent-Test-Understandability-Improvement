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

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        try {
            new LoessInterpolator(0.0, (-605), 0.0);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException expectedException) {
            //
            // number of robustness iterations (-605)
            //
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", expectedException);
        }
    }
}
