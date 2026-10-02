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
public class MullerSolver_ESTest_test5 extends MullerSolver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        MullerSolver mullerSolver0 = new MullerSolver(3.1660099222737955E-7, (-4620.5448992389));
        Ulp ulp0 = new Ulp();
        double double0 = mullerSolver0.solve(2048, (UnivariateFunction) ulp0, 1.5902118682861328);
        assertEquals(1.5902118682861328, double0, 0.01);
    }
}
