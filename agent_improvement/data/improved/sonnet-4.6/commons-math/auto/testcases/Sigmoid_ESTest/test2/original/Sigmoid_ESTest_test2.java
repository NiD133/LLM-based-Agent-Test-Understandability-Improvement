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
public class Sigmoid_ESTest_test2 extends Sigmoid_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        double[] doubleArray0 = new double[1];
        Sigmoid.Parametric sigmoid_Parametric0 = new Sigmoid.Parametric();
        try {
            sigmoid_Parametric0.gradient((-743.321909624), doubleArray0);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // 1 != 2
            //
            verifyException("org.apache.commons.math4.legacy.analysis.function.Sigmoid$Parametric", e);
        }
    }
}
