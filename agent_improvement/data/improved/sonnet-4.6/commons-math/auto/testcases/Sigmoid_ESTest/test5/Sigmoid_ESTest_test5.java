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

    // Sigmoid with lo=0.066... and hi=0.0 effectively inverts the usual [lo,hi] range,
    // so the output at x=0.625 should be a small value near the lower bound.
    @Test(timeout = 4000)
    public void test_applyAsDouble_withInvertedBoundsAndPositiveInput_returnsValueNearLowerBound() throws Throwable {
        double lowerBound = 0.06666666666666667;
        double upperBound = 0.0;
        Sigmoid sigmoid = new Sigmoid(lowerBound, upperBound);

        double result = sigmoid.applyAsDouble(0.625);

        assertEquals(0.023243009022263054, result, 0.01);
    }
}
