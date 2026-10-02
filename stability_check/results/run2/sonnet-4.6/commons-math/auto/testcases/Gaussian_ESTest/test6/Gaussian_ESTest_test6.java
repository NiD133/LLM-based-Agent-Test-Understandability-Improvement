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
public class Gaussian_ESTest_test6 extends Gaussian_ESTest_scaffolding {

    // A Gaussian requires a strictly positive standard deviation; zero is not allowed.
    @Test(timeout = 4000)
    public void constructorShouldThrowWhenStandardDeviationIsZero() throws Throwable {
        Gaussian gaussianWithZeroSigma = null;
        try {
            gaussianWithZeroSigma = new Gaussian(1193.809784545181, 0.0);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            verifyException("org.apache.commons.math4.legacy.analysis.function.Gaussian", e);
        }
    }
}
