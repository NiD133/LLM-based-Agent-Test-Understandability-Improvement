package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Gaussian_ESTest_test4 extends Gaussian_ESTest_scaffolding {

    /**
     * Gaussian.Parametric expects exactly 3 parameters (norm, mean, sigma).
     * Calling gradient() with a parameter array of the wrong length must fail
     * with a RuntimeException reporting the size mismatch ("9 != 3").
     */
    @Test(timeout = 4000)
    public void gradientRejectsParameterArrayOfWrongLength() throws Throwable {
        Gaussian.Parametric gaussian = new Gaussian.Parametric();

        // 9 parameters supplied, but exactly 3 are required.
        double[] wrongLengthParameters = new double[9];
        double anyEvaluationPoint = 1015.1462;

        try {
            gaussian.gradient(anyEvaluationPoint, wrongLengthParameters);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Message indicates the actual vs. expected parameter count: "9 != 3"
            verifyException("org.apache.commons.math4.legacy.analysis.function.Gaussian$Parametric", e);
        }
    }
}
