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

    /**
     * Verifies that for an extremely negative input the sigmoid output converges to its
     * lower asymptote {@code lo}.
     *
     * The sigmoid is defined as: sigmoid(x) = lo + (hi - lo) / (1 + exp(-x)).
     * When x << 0, exp(-x) -> +Inf, so the fraction -> 0 and sigmoid(x) -> lo.
     *
     * Here lo = 33.182076530840796 and hi ≈ 0 (1.22e-16),
     * and the input x = -1023.143076 is far into the left tail, so the expected
     * result is effectively lo itself.
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // Sigmoid bounds: lower asymptote (lo) and upper asymptote (hi ≈ 0)
        double lo = 33.182076530840796;
        double hi = 1.2246467991473532E-16;
        Sigmoid sigmoid = new Sigmoid(lo, hi);

        // DerivativeStructure with 620 free parameters, order 0 (no derivatives),
        // and a very negative value -> sigmoid output should converge to lo
        double veryNegativeInput = -1023.143076;
        DerivativeStructure input = new DerivativeStructure(620, 0, veryNegativeInput);

        DerivativeStructure result = sigmoid.value(input);

        // With such a negative input the sigmoid saturates at its lower bound lo
        assertEquals(lo, result.getValue(), 0.01);
    }
}
