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

    /**
     * Evaluates a sigmoid whose lower and upper asymptotes are very close
     * together (lo = 0.0667, hi = 0.0) and verifies the value at x = 0.625.
     */
    @Test(timeout = 4000)
    public void evaluatesSigmoidWithNearlyEqualAsymptotes() throws Throwable {
        final double lowerAsymptote = 0.06666666666666667;
        final double higherAsymptote = 0.0;
        final double inputValue = 0.625;
        final double expectedValue = 0.023243009022263054;
        final double tolerance = 0.01;

        Sigmoid sigmoid = new Sigmoid(lowerAsymptote, higherAsymptote);

        double actualValue = sigmoid.applyAsDouble(inputValue);

        assertEquals(expectedValue, actualValue, tolerance);
    }
}
