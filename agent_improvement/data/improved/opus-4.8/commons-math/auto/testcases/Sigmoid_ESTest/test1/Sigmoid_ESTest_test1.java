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

    /**
     * A generalized Sigmoid maps its input into the open interval (lo, hi) via
     * lo + (hi - lo) / (1 + exp(-x)). At the input x = 0 the exponential term
     * equals 1, so the result is exactly the midpoint (lo + hi) / 2.
     *
     * Here lo = 0.06666666666666667 and hi = 0.0, giving a midpoint of
     * 0.03333333333333333. The test evaluates the Sigmoid at 0 (carried through
     * a DerivativeStructure) and checks that the value matches that midpoint.
     */
    @Test(timeout = 4000)
    public void valueAtZeroEqualsMidpointOfBounds() throws Throwable {
        final double lowerBound = 0.06666666666666667;
        final double upperBound = 0.0;
        Sigmoid sigmoid = new Sigmoid(lowerBound, upperBound);

        // DerivativeStructure(variableCount, derivativeOrder, value) with value 0.0
        // represents the input point x = 0.
        DerivativeStructure inputAtZero = new DerivativeStructure(4, 4, 0.0);

        DerivativeStructure result = sigmoid.value(inputAtZero);

        final double expectedMidpoint = 0.03333333333333333;
        assertEquals(expectedMidpoint, result.getValue(), 0.01);
    }
}
