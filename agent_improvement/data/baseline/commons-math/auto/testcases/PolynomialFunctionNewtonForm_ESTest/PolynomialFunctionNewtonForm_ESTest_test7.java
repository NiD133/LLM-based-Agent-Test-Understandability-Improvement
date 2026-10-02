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
public class PolynomialFunctionNewtonForm_ESTest_test7 extends PolynomialFunctionNewtonForm_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test7() throws Throwable {
        double[] doubleArray0 = new double[6];
        double[] doubleArray1 = new double[7];
        PolynomialFunctionNewtonForm polynomialFunctionNewtonForm0 = new PolynomialFunctionNewtonForm(doubleArray1, doubleArray0);
        double[] doubleArray2 = polynomialFunctionNewtonForm0.getNewtonCoefficients();
        assertEquals(7, doubleArray2.length);
    }
}
