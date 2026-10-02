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
public class PolynomialFunctionNewtonForm_ESTest_test8 extends PolynomialFunctionNewtonForm_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test8() throws Throwable {
        double[] doubleArray0 = new double[6];
        double[] doubleArray1 = new double[7];
        PolynomialFunctionNewtonForm polynomialFunctionNewtonForm0 = new PolynomialFunctionNewtonForm(doubleArray1, doubleArray0);
        assertEquals(6, polynomialFunctionNewtonForm0.degree());
        double[] doubleArray2 = polynomialFunctionNewtonForm0.getCoefficients();
        assertArrayEquals(new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, doubleArray2, 0.01);
    }
}
