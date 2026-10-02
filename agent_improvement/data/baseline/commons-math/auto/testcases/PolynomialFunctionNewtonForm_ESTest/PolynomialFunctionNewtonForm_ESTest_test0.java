package org.apache.commons.math4.legacy.analysis.polynomials;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PolynomialFunctionNewtonForm_ESTest_test0 extends PolynomialFunctionNewtonForm_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        double[] doubleArray0 = new double[4];
        try {
            PolynomialFunctionNewtonForm.verifyInputArray(doubleArray0, doubleArray0);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // array sizes should have difference 1 (4 != 4 + 1)
            //
            verifyException("org.apache.commons.math4.legacy.analysis.polynomials.PolynomialFunctionNewtonForm", e);
        }
    }
}
