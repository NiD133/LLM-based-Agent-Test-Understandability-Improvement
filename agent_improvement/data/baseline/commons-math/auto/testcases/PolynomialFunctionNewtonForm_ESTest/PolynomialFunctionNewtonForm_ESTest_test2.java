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
public class PolynomialFunctionNewtonForm_ESTest_test2 extends PolynomialFunctionNewtonForm_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        double[] doubleArray0 = new double[0];
        PolynomialFunctionNewtonForm polynomialFunctionNewtonForm0 = null;
        try {
            polynomialFunctionNewtonForm0 = new PolynomialFunctionNewtonForm(doubleArray0, doubleArray0);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // empty polynomials coefficients array
            //
            verifyException("org.apache.commons.math4.legacy.analysis.polynomials.PolynomialFunctionNewtonForm", e);
        }
    }
}
