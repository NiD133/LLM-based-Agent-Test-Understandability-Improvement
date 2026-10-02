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
public class Gaussian_ESTest_test1 extends Gaussian_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        Gaussian standardGaussian = new Gaussian();
        DerivativeStructure zeroParameterPoint = new DerivativeStructure(0, 12, 12);

        DerivativeStructure poweredPoint = DerivativeStructure.pow((double) 12, zeroParameterPoint);
        DerivativeStructure gaussianValue = standardGaussian.value(poweredPoint);

        assertEquals(0.0, gaussianValue.getValue(), 0.01);
    }
}
