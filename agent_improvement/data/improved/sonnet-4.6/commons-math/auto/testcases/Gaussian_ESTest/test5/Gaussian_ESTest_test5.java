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
public class Gaussian_ESTest_test5 extends Gaussian_ESTest_scaffolding {

    // Gaussian.Parametric.gradient() requires a non-null parameters array;
    // passing null should throw NullPointerException immediately.
    @Test(timeout = 4000)
    public void test_gradient_throwsNullPointerException_whenParametersArrayIsNull() throws Throwable {
        Gaussian.Parametric parametricGaussian = new Gaussian.Parametric();
        double x = 0.9166666666666666;
        double[] nullParameters = null;

        try {
            parametricGaussian.gradient(x, nullParameters);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.math4.legacy.analysis.function.Gaussian$Parametric", e);
        }
    }
}
