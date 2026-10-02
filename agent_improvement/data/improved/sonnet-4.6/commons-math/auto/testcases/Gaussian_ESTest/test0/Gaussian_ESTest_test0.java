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
public class Gaussian_ESTest_test0 extends Gaussian_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_parametricValue_returnsZero_whenNormIsZero() throws Throwable {
        Gaussian.Parametric parametric = new Gaussian.Parametric();

        // Parameters: [norm=0.0, mean=0.0, sigma=2.0]
        double[] params = new double[3];
        params[2] = 2.0; // sigma

        double result = parametric.value(2.0, params);

        // norm=0 means the Gaussian amplitude is 0, so value must be 0
        assertEquals(0.0, result, 0.01);
    }
}
