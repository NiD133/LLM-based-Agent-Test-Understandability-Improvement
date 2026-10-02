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
public class Sigmoid_ESTest_test7 extends Sigmoid_ESTest_scaffolding {

    /**
     * The parametric sigmoid is f(x) = lo + (hi - lo) / (1 + exp(-x)).
     * With both parameters set to zero (lo = 0, hi = 0), the curve collapses
     * to a constant zero, so evaluating it at any x must return 0.
     */
    @Test(timeout = 4000)
    public void valueWithZeroLowAndHighParametersReturnsZero() throws Throwable {
        Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();
        double[] zeroLowAndHighParameters = new double[] { 0.0, 0.0 };

        double result = sigmoid.value(0.0, zeroLowAndHighParameters);

        assertEquals(0.0, result, 0.01);
    }
}
