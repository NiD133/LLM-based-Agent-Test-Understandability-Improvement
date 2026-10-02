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
public class Sigmoid_ESTest_test7 extends Sigmoid_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test7() throws Throwable {
        Sigmoid.Parametric sigmoid_Parametric0 = new Sigmoid.Parametric();
        double[] doubleArray0 = new double[2];
        double double0 = sigmoid_Parametric0.value(0.0, doubleArray0);
        assertEquals(0.0, double0, 0.01);
    }
}
