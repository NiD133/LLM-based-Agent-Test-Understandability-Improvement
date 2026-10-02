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
    public void valueWithDerivativeStructureUsesConfiguredBounds() throws Throwable {
        double lowerAsymptote = 0.06666666666666667;
        double upperAsymptote = 0.0;
        Sigmoid sigmoid = new Sigmoid(lowerAsymptote, upperAsymptote);

        int freeParameters = 4;
        int derivationOrder = 4;
        double inputValue = 0.0;
        DerivativeStructure input = new DerivativeStructure(freeParameters, derivationOrder, inputValue);

        DerivativeStructure result = sigmoid.value(input);

        double expectedValue = 0.03333333333333333;
        double tolerance = 0.01;
        assertEquals(expectedValue, result.getValue(), tolerance);
    }
}
