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
        Gaussian gaussian0 = new Gaussian();
        DerivativeStructure derivativeStructure0 = new DerivativeStructure(0, 12, 12);
        DerivativeStructure derivativeStructure1 = DerivativeStructure.pow((double) 12, derivativeStructure0);
        DerivativeStructure derivativeStructure2 = gaussian0.value(derivativeStructure1);
        assertEquals(0.0, derivativeStructure2.getValue(), 0.01);
    }
}
