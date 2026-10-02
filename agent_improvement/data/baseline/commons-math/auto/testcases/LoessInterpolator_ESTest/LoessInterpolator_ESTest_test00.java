package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test00 extends LoessInterpolator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        double[] doubleArray0 = new double[6];
        double[] doubleArray1 = new double[3];
        LoessInterpolator loessInterpolator0 = new LoessInterpolator();
        try {
            loessInterpolator0.smooth(doubleArray1, doubleArray0);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // 3 != 6
            //
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
