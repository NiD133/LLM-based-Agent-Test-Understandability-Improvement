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
     * Sigmoid(lo, hi) maps the real line to [lo, hi].
     * At x=0 the logistic value is 0.5, so the output is lo + (hi - lo) * 0.5.
     * With lo=1/15 and hi=0: result = 1/15 + (0 - 1/15) * 0.5 = 1/30.
     */
    @Test(timeout = 4000)
    public void test_sigmoidAtZeroWithCustomBoundsReturnsInterpolatedMidpoint() throws Throwable {
        double lo = 0.06666666666666667; // 1/15
        double hi = 0.0;
        Sigmoid sigmoid = new Sigmoid(lo, hi);

        // DerivativeStructure with 4 free variables, order 4, evaluated at x=0
        DerivativeStructure inputAtZero = new DerivativeStructure(4, 4, 0.0);

        DerivativeStructure result = sigmoid.value(inputAtZero);

        // Expected: lo + (hi - lo) * 0.5 = 1/30 ≈ 0.03333...
        double expectedMidpoint = 0.03333333333333333;
        assertEquals(expectedMidpoint, result.getValue(), 0.01);
    }
}
