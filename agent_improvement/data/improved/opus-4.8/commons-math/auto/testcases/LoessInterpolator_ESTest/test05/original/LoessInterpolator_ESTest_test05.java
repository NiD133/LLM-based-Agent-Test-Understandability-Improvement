package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test05 extends LoessInterpolator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        LoessInterpolator loessInterpolator0 = new LoessInterpolator();
        double[] doubleArray0 = new double[1];
        try {
            loessInterpolator0.interpolate(doubleArray0, doubleArray0);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // number of points (1)
            //
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.SplineInterpolator", e);
        }
    }
}
