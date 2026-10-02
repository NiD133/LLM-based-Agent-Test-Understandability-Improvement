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
public class Gaussian_ESTest_test3 extends Gaussian_ESTest_scaffolding {

    /**
     * Verifies the partial derivatives produced by {@link Gaussian.Parametric#gradient(double, double[])}.
     *
     * The Gaussian is parameterised by {norm, mean, sigma}. Here the parameters are
     * {norm = 0, mean = 0, sigma = 1530} and the function is evaluated at x = 1530.
     * The gradient returns the partial derivatives with respect to {norm, mean, sigma}.
     */
    @Test(timeout = 4000)
    public void gradientReturnsExpectedPartialDerivatives() throws Throwable {
        Gaussian.Parametric parametricGaussian = new Gaussian.Parametric();

        // Parameters: norm = 0, mean = 0, sigma = 1530
        double[] parameters = new double[] { 0.0, 0.0, 1530.0 };
        double evaluationPoint = 1530.0;

        double[] gradient = parametricGaussian.gradient(evaluationPoint, parameters);

        double[] expectedGradient = { 0.6065306597126334, 0.0, 0.0 };
        assertArrayEquals(expectedGradient, gradient, 0.01);
    }
}
