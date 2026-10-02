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
    public void test2_solvesCubeRootFunctionNearZeroWithinSearchInterval() throws Throwable {
        // The cube root function (cbrt) has its only root at x=0.
        // We verify that MullerSolver finds a value very close to zero
        // when searching in the interval [-1321.9, 2331.0].
        MullerSolver solver = new MullerSolver();
        Cbrt cubeRootFunction = new Cbrt();

        int maxEvaluations = 2331;
        double lowerBound = -1321.9;
        double upperBound = (double) 2331;
        double expectedRoot = 3.060102951752152E-7;
        double tolerance = 0.01;

        double root = solver.solve(maxEvaluations, (UnivariateFunction) cubeRootFunction, lowerBound, upperBound);

        assertEquals(expectedRoot, root, tolerance);
    }
}
