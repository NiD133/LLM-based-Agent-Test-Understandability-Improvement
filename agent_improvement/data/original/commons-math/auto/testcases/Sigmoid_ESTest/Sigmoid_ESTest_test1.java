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
public class Sigmoid_ESTest_test1 extends Sigmoid_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        Sigmoid sigmoid0 = new Sigmoid(0.06666666666666667, 0.0);
        DerivativeStructure derivativeStructure0 = new DerivativeStructure(4, 4, 0.0);
        DerivativeStructure derivativeStructure1 = sigmoid0.value(derivativeStructure0);
        assertEquals(0.03333333333333333, derivativeStructure1.getValue(), 0.01);
    }
}
