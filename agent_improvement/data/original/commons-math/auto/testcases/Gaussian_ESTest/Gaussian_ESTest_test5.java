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

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        Gaussian.Parametric gaussian_Parametric0 = new Gaussian.Parametric();
        try {
            gaussian_Parametric0.gradient(0.9166666666666666, (double[]) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // null is not allowed
            //
            verifyException("org.apache.commons.math4.legacy.analysis.function.Gaussian$Parametric", e);
        }
    }
}
