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

    // Gradient of the parametric sigmoid w.r.t. [lo, hi] at a strongly negative x
    // approaches [1.0, 0.0] because sigmoid(-20) ≈ 0, so the function is nearly at its lower asymptote.
    @Test(timeout = 4000)
    public void test_gradientAtStronglyNegativeX_approachesLowerAsymptoteGradient() throws Throwable {
        Sigmoid.Parametric parametricSigmoid = new Sigmoid.Parametric();
        double[] defaultParameters = new double[2]; // [lo=0.0, hi=0.0]

        double x = -20.0;
        double[] gradient = parametricSigmoid.gradient(x, defaultParameters);

        // At x = -20, sigmoid ≈ 0, so gradient w.r.t. lo ≈ 1 and w.r.t. hi ≈ 0
        double[] expectedGradient = new double[] { 0.9999999979388464, 2.0611536181902037E-9 };
        assertArrayEquals(expectedGradient, gradient, 0.01);
    }
}
