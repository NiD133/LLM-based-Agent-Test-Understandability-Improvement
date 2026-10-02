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

    private static final double EVALUATION_POINT = 1530.0;
    private static final double NORMALIZATION = 0.0;
    private static final double MEAN = 0.0;
    private static final double SIGMA = 1530.0;
    private static final double ASSERTION_TOLERANCE = 0.01;

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        Gaussian.Parametric gaussian = new Gaussian.Parametric();
        double[] parameters = new double[3];
        parameters[2] = SIGMA;

        double[] gradient = gaussian.gradient(EVALUATION_POINT, parameters);

        double[] expectedGradient = new double[] {0.6065306597126334, NORMALIZATION, MEAN};
        assertArrayEquals(expectedGradient, gradient, ASSERTION_TOLERANCE);
    }
}
