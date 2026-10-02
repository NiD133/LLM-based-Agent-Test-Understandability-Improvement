package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test06 extends LoessInterpolator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        LoessInterpolator loessInterpolator0 = new LoessInterpolator();
        double[] doubleArray0 = new double[4];
        doubleArray0[1] = 0.3;
        doubleArray0[2] = (double) 2;
        doubleArray0[3] = 3745.0491385;
        try {
            loessInterpolator0.smooth(doubleArray0, doubleArray0);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // bandwidth (1)
            //
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
