package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.QuinticFunction;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Expm1;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.legacy.exception.NoBracketingException;
import org.apache.commons.math4.legacy.exception.NumberIsTooLargeException;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class MullerSolverTest_testQuinticFunction {

    private static final int MAX_EVAL = 100;

    /**
     * Computes the solver tolerance as the larger of the absolute accuracy and
     * the scaled relative accuracy at the expected root value.
     */
    private static double tolerance(UnivariateSolver solver, double expected) {
        return JdkMath.max(
            solver.getAbsoluteAccuracy(),
            JdkMath.abs(expected * solver.getRelativeAccuracy())
        );
    }

    /**
     * Verifies that the MullerSolver finds a root of the QuinticFunction
     * within [min, max] and that the result is within solver tolerance of the
     * expected root.
     */
    private static void assertRootFound(UnivariateSolver solver,
                                        UnivariateFunction f,
                                        double min, double max,
                                        double expectedRoot) {
        double result = solver.solve(MAX_EVAL, f, min, max);
        Assert.assertEquals(expectedRoot, result, tolerance(solver, expectedRoot));
    }

    /**
     * Test of solver for the quintic function.
     *
     * The QuinticFunction has known roots at x = -0.5, x = 0, and x = 1.
     * Each sub-test brackets exactly one root and confirms the solver converges
     * to it within the solver's own accuracy thresholds.
     */
    @Test
    public void testQuinticFunction() {
        UnivariateFunction f = new QuinticFunction();
        UnivariateSolver solver = new MullerSolver();

        // Root at x = 0, bracketed by [-0.4, 0.2]
        assertRootFound(solver, f, -0.4, 0.2, 0.0);

        // Root at x = 1, bracketed by [0.75, 1.5]
        assertRootFound(solver, f, 0.75, 1.5, 1.0);

        // Root at x = -0.5, bracketed by [-0.9, -0.2]
        assertRootFound(solver, f, -0.9, -0.2, -0.5);
    }
}
