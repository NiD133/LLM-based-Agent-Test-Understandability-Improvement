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

    /**
     * Solving cube-root(x) = 0 over a bracketing interval should converge to
     * the function's only root, which lies at x = 0. Because the cube root is
     * very flat near zero, the solver stops at a tiny non-zero approximation,
     * so the assertion uses a coarse tolerance.
     */
    @Test(timeout = 4000)
    public void testSolveCubeRootConvergesNearZero() throws Throwable {
        MullerSolver solver = new MullerSolver();
        Cbrt cubeRoot = new Cbrt();

        int maxEvaluations = 2331;
        double intervalStart = -1321.9;
        double intervalEnd = 2331.0;

        double root = solver.solve(maxEvaluations, (UnivariateFunction) cubeRoot, intervalStart, intervalEnd);

        double expectedRootNearZero = 3.060102951752152E-7;
        double tolerance = 0.01;
        assertEquals(expectedRootNearZero, root, tolerance);
    }
}
