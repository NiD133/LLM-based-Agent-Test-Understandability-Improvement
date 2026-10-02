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
     * <p>The parametric sigmoid is defined by two parameters, {@code lo} and {@code hi}.
     * Here both are left at their default value of {@code 0.0}, and the gradient is
     * evaluated at {@code x = -20.0}. The result contains the derivatives with respect
     * to {@code lo} and {@code hi} respectively.</p>
     */
    @Test(timeout = 4000)
    public void test6() throws Throwable {
        Sigmoid.Parametric sigmoidParametric = new Sigmoid.Parametric();

        double evaluationPoint = -20.0;
        double[] parameters = new double[] { 0.0, 0.0 }; // lo, hi

        double[] gradient = sigmoidParametric.gradient(evaluationPoint, parameters);

        double[] expectedGradient = { 0.9999999979388464, 2.0611536181902037E-9 };
        double tolerance = 0.01;
        assertArrayEquals(expectedGradient, gradient, tolerance);
    }
}
