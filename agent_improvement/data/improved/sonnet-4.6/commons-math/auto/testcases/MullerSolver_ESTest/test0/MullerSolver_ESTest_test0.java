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

    // The cube root function cbrt(x) = x^(1/3) has exactly one real root at x = 0.
    // MullerSolver should locate that root within the given search interval.
    @Test(timeout = 4000)
    public void test0_solvesCubeRootFunctionAndFindsRootAtZero() throws Throwable {
        MullerSolver mullerSolver = new MullerSolver();
        Cbrt cubeRootFunction = new Cbrt();

        int maxEvaluations = 1392203;
        double searchMin = -2825.0795928397;
        double searchMax = 2.7950664235456177E85;
        double toleranceDelta = 0.01;

        double root = mullerSolver.solve(maxEvaluations, (UnivariateFunction) cubeRootFunction, searchMin, searchMax);

        assertEquals(0.0, root, toleranceDelta);
    }
}
