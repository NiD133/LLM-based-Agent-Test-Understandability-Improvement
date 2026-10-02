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
public class Sigmoid_ESTest_test5 extends Sigmoid_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        Sigmoid sigmoid0 = new Sigmoid(0.06666666666666667, 0.0);
        double double0 = sigmoid0.applyAsDouble(0.625);
        assertEquals(0.023243009022263054, double0, 0.01);
    }
}
