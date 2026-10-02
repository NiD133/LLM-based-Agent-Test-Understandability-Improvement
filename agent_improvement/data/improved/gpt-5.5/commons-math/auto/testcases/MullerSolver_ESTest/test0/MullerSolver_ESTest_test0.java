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
public class MullerSolver_ESTest_test0 extends MullerSolver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        MullerSolver solver = new MullerSolver();
        Cbrt cubeRootFunction = new Cbrt();

        double root = solver.solve(
                1392203,
                (UnivariateFunction) cubeRootFunction,
                (-2825.0795928397),
                2.7950664235456177E85);

        assertEquals(0.0, root, 0.01);
    }
}
