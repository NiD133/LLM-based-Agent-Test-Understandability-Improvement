package org.apache.commons.math4.legacy.analysis.solvers;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Cbrt;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.legacy.analysis.function.Ulp;
import org.apache.commons.math4.legacy.analysis.polynomials.PolynomialFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MullerSolver_ESTest_test3 extends MullerSolver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        MullerSolver mullerSolver0 = new MullerSolver(1.0E-15);
        double[] doubleArray0 = new double[4];
        PolynomialFunction polynomialFunction0 = new PolynomialFunction(doubleArray0);
        double double0 = mullerSolver0.solve(5575, (UnivariateFunction) polynomialFunction0, 0.0016310069148432287);
        assertEquals(Double.NaN, double0, 0.01);
    }
}
