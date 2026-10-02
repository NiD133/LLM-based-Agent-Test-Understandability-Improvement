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
public class PolynomialFunctionNewtonForm_ESTest_test6 extends PolynomialFunctionNewtonForm_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        double[] doubleArray0 = new double[4];
        double[] doubleArray1 = new double[3];
        PolynomialFunctionNewtonForm polynomialFunctionNewtonForm0 = new PolynomialFunctionNewtonForm(doubleArray0, doubleArray1);
        double[] doubleArray2 = polynomialFunctionNewtonForm0.getCenters();
        assertEquals(3, doubleArray2.length);
    }
}
