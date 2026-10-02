package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test03 extends LoessInterpolator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        double[] doubleArray0 = new double[4];
        doubleArray0[1] = 0.3;
        doubleArray0[2] = (double) 2;
        doubleArray0[3] = 3745.0491385;
        LoessInterpolator loessInterpolator0 = new LoessInterpolator(0.9999999999999997, 2, 1);
        double[] doubleArray1 = loessInterpolator0.smooth(doubleArray0, doubleArray0);
        assertArrayEquals(new double[] { 0.14923934718433918, 0.1512398973385424, 1.907630988400478, 3745.0491290577315 }, doubleArray1, 0.01);
    }
}
