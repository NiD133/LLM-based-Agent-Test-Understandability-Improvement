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
     * Verifies that evaluating the Sigmoid for a very large negative input drives the
     * result to the lower asymptote of the configured curve.
     *
     * <p>A {@link Sigmoid} is bounded below by {@code lo} and above by {@code hi}. For a
     * strongly negative argument the standard logistic curve saturates at its lower bound,
     * so {@code value(x)} is expected to equal {@code lo}.</p>
     */
    @Test(timeout = 4000)
    public void valueForLargeNegativeInputEqualsLowerAsymptote() throws Throwable {
        final double lowerAsymptote = 33.182076530840796;
        final double upperAsymptote = 1.2246467991473532E-16;
        Sigmoid sigmoid = new Sigmoid(lowerAsymptote, upperAsymptote);

        // A DerivativeStructure with 620 free parameters, derivation order 0, holding a large
        // negative value. Order 0 means only the function value (no derivatives) is tracked.
        final int freeParameters = 620;
        final int derivationOrder = 0;
        final double largeNegativeInput = -1023.143076;
        DerivativeStructure input =
                new DerivativeStructure(freeParameters, derivationOrder, largeNegativeInput);

        DerivativeStructure result = sigmoid.value(input);

        assertEquals(lowerAsymptote, result.getValue(), 0.01);
    }
}
