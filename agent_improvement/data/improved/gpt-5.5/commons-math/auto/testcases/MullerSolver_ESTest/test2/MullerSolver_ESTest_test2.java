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

    private static final int MAX_EVALUATIONS = 2331;
    private static final double SEARCH_MINIMUM = -1321.9;
    private static final double EXPECTED_ROOT = 3.060102951752152E-7;
    private static final double ASSERTION_TOLERANCE = 0.01;

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        MullerSolver solver = new MullerSolver();
        Cbrt cubeRootFunction = new Cbrt();

        double actualRoot = solver.solve(
                MAX_EVALUATIONS,
                (UnivariateFunction) cubeRootFunction,
                SEARCH_MINIMUM,
                (double) MAX_EVALUATIONS);

        assertEquals(EXPECTED_ROOT, actualRoot, ASSERTION_TOLERANCE);
    }
}
