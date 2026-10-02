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
    public void test2() throws Throwable {
        Gaussian standardGaussian = new Gaussian();
        DerivativeStructure derivativeInput = new DerivativeStructure(0, 12, 12);

        DerivativeStructure evaluatedDerivative = standardGaussian.value(derivativeInput);

        assertEquals(2.1463837356630605E-32, evaluatedDerivative.getValue(), 0.01);
    }
}
