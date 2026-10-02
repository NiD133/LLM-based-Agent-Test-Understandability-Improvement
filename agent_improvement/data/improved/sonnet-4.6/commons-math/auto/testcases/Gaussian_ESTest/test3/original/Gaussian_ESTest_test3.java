package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Gaussian_ESTest_test3 extends Gaussian_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        Gaussian.Parametric gaussian_Parametric0 = new Gaussian.Parametric();
        double[] doubleArray0 = new double[3];
        doubleArray0[2] = 1530.0;
        double[] doubleArray1 = gaussian_Parametric0.gradient(1530.0, doubleArray0);
        assertArrayEquals(new double[] { 0.6065306597126334, 0.0, 0.0 }, doubleArray1, 0.01);
    }
}
