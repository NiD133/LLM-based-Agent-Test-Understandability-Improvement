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
     * When x equals sigma and norm/mean are zero, the first component of the gradient
     * (partial derivative w.r.t. norm) equals exp(-0.5) ≈ 0.6065.
     * The other gradient components are zero because they are scaled by norm = 0.
     */
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        Gaussian.Parametric parametric = new Gaussian.Parametric();

        // Parameters: [norm=0.0, mean=0.0, sigma=1530.0]
        double[] params = new double[3];
        params[2] = 1530.0; // sigma

        double x = 1530.0; // x equals sigma
        double[] gradient = parametric.gradient(x, params);

        assertArrayEquals(new double[] { 0.6065306597126334, 0.0, 0.0 }, gradient, 0.01);
    }
}
