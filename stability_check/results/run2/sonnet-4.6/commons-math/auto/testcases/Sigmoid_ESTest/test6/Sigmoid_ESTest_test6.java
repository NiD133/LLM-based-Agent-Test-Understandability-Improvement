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
     * Tests that Sigmoid.Parametric.gradient returns correct partial derivatives
     * at x = -20 with the default lower/upper bounds [0, 1].
     *
     * At x = -20, the sigmoid value is extremely close to 0, so:
     *   - gradient[0] (∂/∂lower) ≈ 1 (almost all weight is on the lower bound)
     *   - gradient[1] (∂/∂upper) ≈ 0 (almost no weight is on the upper bound)
     */
    @Test(timeout = 4000)
    public void test_gradient_atFarLeftInput_returnsExpectedPartialDerivatives() throws Throwable {
        Sigmoid.Parametric parametricSigmoid = new Sigmoid.Parametric();

        // Parameters: [lower bound, upper bound] — both initialised to 0.0
        double[] parameters = new double[2];
        double x = -20.0;

        double[] gradient = parametricSigmoid.gradient(x, parameters);

        // At x = -20, sigmoid ≈ 0, so derivative w.r.t. lower bound ≈ 1
        // and derivative w.r.t. upper bound ≈ 0
        double[] expectedGradient = { 0.9999999979388464, 2.0611536181902037E-9 };
        assertArrayEquals(expectedGradient, gradient, 0.01);
    }
}
