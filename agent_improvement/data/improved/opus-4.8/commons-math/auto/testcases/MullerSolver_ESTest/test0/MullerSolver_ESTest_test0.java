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

    /**
     * Solving the cube-root function over an interval that brackets the origin
     * should converge to its only real root, x = 0.
     */
    @Test(timeout = 4000)
    public void solveCubeRootConvergesToZero() throws Throwable {
        MullerSolver solver = new MullerSolver();
        UnivariateFunction cubeRoot = new Cbrt();

        // The bracket spans a negative lower bound and a very large positive
        // upper bound, so it encloses the function's root at the origin.
        int maxEvaluations = 1392203;
        double lowerBound = -2825.0795928397;
        double upperBound = 2.7950664235456177E85;

        double root = solver.solve(maxEvaluations, cubeRoot, lowerBound, upperBound);

        assertEquals(0.0, root, 0.01);
    }
}
