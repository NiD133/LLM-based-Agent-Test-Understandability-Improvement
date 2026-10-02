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

    @Test(timeout = 4000)
    public void test7() throws Throwable {
        Gaussian.Parametric parametricGaussian = new Gaussian.Parametric();
        double[] invalidParameters = new double[3];

        try {
            parametricGaussian.value(2.0, invalidParameters);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // 0 is smaller than, or equal to, the minimum (0)
            //
            verifyException("org.apache.commons.math4.legacy.analysis.function.Gaussian$Parametric", e);
        }
    }
}
