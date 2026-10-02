package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Gaussian_ESTest_test5 extends Gaussian_ESTest_scaffolding {

    /**
     * Gaussian.Parametric.gradient(x, parameters) requires a non-null parameter
     * array (it reads norm, mean and sigma from it). Passing null must therefore
     * fail fast with a NullPointerException originating from Gaussian.Parametric.
     */
    @Test(timeout = 4000)
    public void gradientWithNullParametersThrowsNullPointerException() throws Throwable {
        Gaussian.Parametric gaussianParametric = new Gaussian.Parametric();

        double anyEvaluationPoint = 0.9166666666666666;
        double[] nullParameters = null;

        try {
            gaussianParametric.gradient(anyEvaluationPoint, nullParameters);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // null parameter array is not allowed
            verifyException("org.apache.commons.math4.legacy.analysis.function.Gaussian$Parametric", e);
        }
    }
}
