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

    // Default sigmoid uses lo=0.0 and hi=1.0 as the two parameters
    private static final int NUM_SIGMOID_PARAMETERS = 2;

    // x = -20 is far into the left tail where sigmoid(x) ≈ 0,
    // so d/d(lo) ≈ 1 and d/d(hi) ≈ 0
    private static final double X_FAR_LEFT = -20.0;

    private static final double DELTA = 0.01;

    @Test(timeout = 4000)
    public void test_gradient_atFarLeftInput_returnsExpectedPartialDerivatives() throws Throwable {
        Sigmoid.Parametric parametricSigmoid = new Sigmoid.Parametric();

        // Parameters for the default sigmoid: [lo, hi] = [0.0, 0.0] (zero-initialised)
        double[] parameters = new double[NUM_SIGMOID_PARAMETERS];

        double[] gradient = parametricSigmoid.gradient(X_FAR_LEFT, parameters);

        // At x = -20 the sigmoid value is nearly 0, so the gradient with respect
        // to 'lo' (lower asymptote) is close to 1 and with respect to 'hi'
        // (upper asymptote) is close to 0.
        double[] expectedGradient = { 0.9999999979388464, 2.0611536181902037E-9 };
        assertArrayEquals(expectedGradient, gradient, DELTA);
    }
}
