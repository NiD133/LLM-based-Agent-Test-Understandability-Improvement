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
public class MullerSolver_ESTest_test2 extends MullerSolver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        MullerSolver mullerSolver0 = new MullerSolver();
        Cbrt cbrt0 = new Cbrt();
        double double0 = mullerSolver0.solve(2331, (UnivariateFunction) cbrt0, (-1321.9), (double) 2331);
        assertEquals(3.060102951752152E-7, double0, 0.01);
    }
}
