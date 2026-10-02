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
public class Gaussian_ESTest_test7 extends Gaussian_ESTest_scaffolding {

    /**
     * Gaussian.Parametric#value expects the parameters {norm, mean, sigma}.
     * A sigma (standard deviation) of 0 is invalid, so passing an all-zero
     * parameter array must trigger a RuntimeException reporting that the
     * standard deviation is not greater than the minimum allowed value (0).
     */
    @Test(timeout = 4000)
    public void testValueRejectsZeroStandardDeviation() throws Throwable {
        Gaussian.Parametric gaussian = new Gaussian.Parametric();

        // {norm, mean, sigma} = {0, 0, 0}; sigma == 0 is the offending value.
        double[] parametersWithZeroSigma = new double[3];
        double x = 2.0;

        try {
            gaussian.value(x, parametersWithZeroSigma);
            fail("Expected a RuntimeException because the standard deviation (sigma) is 0");
        } catch (RuntimeException e) {
            // Message: "0 is smaller than, or equal to, the minimum (0)"
            verifyException("org.apache.commons.math4.legacy.analysis.function.Gaussian$Parametric", e);
        }
    }
}
