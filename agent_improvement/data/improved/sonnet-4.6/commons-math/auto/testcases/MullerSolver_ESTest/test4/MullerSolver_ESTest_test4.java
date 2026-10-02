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
public class MullerSolver_ESTest_test4 extends MullerSolver_ESTest_scaffolding {

    // The cube root function has a root at zero; this range spans from a large negative value
    // to a very small positive value near zero, so the solver should converge near the upper bound.
    private static final int    MAX_EVALUATIONS       = 2331;
    private static final double SEARCH_MIN            = -1476.2544696353405;
    private static final double SEARCH_MAX            = 4.800501435803201E-220;
    private static final double EXPECTED_ROOT         = 4.800501435803201E-220;
    private static final double RESULT_TOLERANCE      = 0.01;

    @Test(timeout = 4000)
    public void test4_solvesCubeRootFunctionNearZeroUpperBound() throws Throwable {
        MullerSolver mullerSolver = new MullerSolver();
        Cbrt cubeRootFunction = new Cbrt();

        double root = mullerSolver.solve(MAX_EVALUATIONS, (UnivariateFunction) cubeRootFunction, SEARCH_MIN, SEARCH_MAX);

        assertEquals(EXPECTED_ROOT, root, RESULT_TOLERANCE);
    }
}
