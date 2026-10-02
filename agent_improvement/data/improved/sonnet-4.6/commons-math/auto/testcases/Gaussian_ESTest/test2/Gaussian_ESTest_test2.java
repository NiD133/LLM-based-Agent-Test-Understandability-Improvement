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
public class Gaussian_ESTest_test2 extends Gaussian_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2_defaultGaussianValueAtX12AsDerivativeStructure() throws Throwable {
        // Standard normal Gaussian: mean=0, sigma=1
        Gaussian gaussian = new Gaussian();

        // DerivativeStructure(freeVariables=0, order=12, value=12.0): constant 12, no free variables
        DerivativeStructure inputX12 = new DerivativeStructure(0, 12, 12);

        DerivativeStructure result = gaussian.value(inputX12);

        // Gaussian(0,1) evaluated at x=12: (1/sqrt(2π)) * exp(-72) ≈ 2.146e-32
        assertEquals(2.1463837356630605E-32, result.getValue(), 0.01);
    }
}
