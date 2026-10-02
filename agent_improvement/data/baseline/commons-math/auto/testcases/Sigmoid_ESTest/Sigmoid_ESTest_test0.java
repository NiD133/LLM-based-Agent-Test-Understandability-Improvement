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
public class Sigmoid_ESTest_test0 extends Sigmoid_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        Sigmoid sigmoid0 = new Sigmoid(33.182076530840796, 1.2246467991473532E-16);
        DerivativeStructure derivativeStructure0 = new DerivativeStructure(620, 0, (-1023.143076));
        DerivativeStructure derivativeStructure1 = sigmoid0.value(derivativeStructure0);
        assertEquals(33.182076530840796, derivativeStructure1.getValue(), 0.01);
    }
}
