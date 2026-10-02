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
     * Verifies the partial derivatives returned by {@link Sigmoid.Parametric#gradient}.
     *
     * <p>The gradient is evaluated at x = -20 with the default parameters
     * (lower asymptote = 0, higher asymptote = 0, both supplied as zeros).
     * The result is the vector of derivatives with respect to those two
     * parameters, which is compared against the expected values with a
     * tolerance of 0.01.</p>
     */
    @Test(timeout = 4000)
    public void gradientAtMinus20ReturnsExpectedPartialDerivatives() throws Throwable {
        Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();

        double x = -20.0;
        double[] parameters = new double[2];
        double[] gradient = sigmoid.gradient(x, parameters);

        double[] expectedGradient = { 0.9999999979388464, 2.0611536181902037E-9 };
        assertArrayEquals(expectedGradient, gradient, 0.01);
    }
}
