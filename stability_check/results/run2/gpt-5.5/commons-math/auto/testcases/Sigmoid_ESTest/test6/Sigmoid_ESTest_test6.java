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
public class Sigmoid_ESTest_test6 extends Sigmoid_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        Sigmoid.Parametric sigmoidFunction = new Sigmoid.Parametric();
        double[] parameters = new double[2];

        double[] gradient = sigmoidFunction.gradient((-20.0), parameters);

        double[] expectedGradient = new double[] { 0.9999999979388464, 2.0611536181902037E-9 };
        assertArrayEquals(expectedGradient, gradient, 0.01);
    }
}
