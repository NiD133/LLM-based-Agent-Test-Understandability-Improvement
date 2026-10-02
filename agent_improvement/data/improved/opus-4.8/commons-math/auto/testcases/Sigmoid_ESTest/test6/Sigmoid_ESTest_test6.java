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
public class Sigmoid_ESTest_test6 extends Sigmoid_ESTest_scaffolding {

    /**
     * Verifies the gradient of the parametric Sigmoid with respect to its two
     * parameters (lower asymptote "lo" and higher asymptote "hi").
     *
     * For input x = -20, the gradient should be approximately:
     *   d/d(lo) ~ 0.99999999..., d/d(hi) ~ 2.06e-9
     */
    @Test(timeout = 4000)
    public void test6() throws Throwable {
        Sigmoid.Parametric parametricSigmoid = new Sigmoid.Parametric();

        // The two parameters [lo, hi]; their actual values do not affect the
        // gradient computation, so zeros are used here.
        double[] parameters = new double[2];

        double x = -20.0;
        double[] gradient = parametricSigmoid.gradient(x, parameters);

        double[] expectedGradient = { 0.9999999979388464, 2.0611536181902037E-9 };
        double tolerance = 0.01;
        assertArrayEquals(expectedGradient, gradient, tolerance);
    }
}
