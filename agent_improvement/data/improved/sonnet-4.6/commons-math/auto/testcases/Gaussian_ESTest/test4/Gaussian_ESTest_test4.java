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
public class Gaussian_ESTest_test4 extends Gaussian_ESTest_scaffolding {

    /**
     * Verifies that Gaussian.Parametric.gradient() throws a RuntimeException
     * when given a parameter array whose length is not exactly 3.
     *
     * The Gaussian parametric form requires exactly 3 parameters:
     *   [norm, mean, sigma]. Passing a 9-element array must be rejected.
     */
    @Test(timeout = 4000)
    public void test_gradient_throwsException_whenParameterArrayLengthIsNot3() throws Throwable {
        Gaussian.Parametric gaussianParametric = new Gaussian.Parametric();

        // The Gaussian parametric gradient method expects exactly 3 parameters
        // (norm, mean, sigma), so an array of 9 elements must cause an exception.
        double[] wrongSizeParams = new double[9];
        double inputValue = 1015.1462;

        try {
            gaussianParametric.gradient(inputValue, wrongSizeParams);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Error message should indicate the mismatch: "9 != 3"
            verifyException("org.apache.commons.math4.legacy.analysis.function.Gaussian$Parametric", e);
        }
    }
}
