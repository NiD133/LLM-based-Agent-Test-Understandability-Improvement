package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.QuinticFunction;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the MullerSolver on the quintic function f(x) = x^5 - x^4 - 5x^3 + 5x^2 + 4x - 4,
 * which has roots at x = -2, -1, 0, 1, 2 (not all necessarily found depending on bracket).
 */
public class MullerSolverTest_testQuinticFunction {

    private static final int MAX_EVAL = 100;

    /**
     * Verifies that the solver finds the expected root within the given bracket [min, max].
     * Tolerance is derived from the solver's own absolute and relative accuracy settings.
     */
    private void assertRootFound(UnivariateSolver solver, UnivariateFunction f,
                                 double min, double max, double expectedRoot) {
        double tolerance = JdkMath.max(
            solver.getAbsoluteAccuracy(),
            JdkMath.abs(expectedRoot * solver.getRelativeAccuracy())
        );
        double result = solver.solve(MAX_EVAL, f, min, max);
        Assert.assertEquals(expectedRoot, result, tolerance);
    }

    /**
     * Tests that MullerSolver correctly locates three distinct roots of the quintic function
     * using separate bracketing intervals that isolate each root.
     */
    @Test
    public void testQuinticFunction() {
        UnivariateFunction quintic = new QuinticFunction();
        UnivariateSolver solver = new MullerSolver();

        // Root near x = 0, bracketed by [-0.4, 0.2]
        assertRootFound(solver, quintic, -0.4, 0.2, 0.0);

        // Root near x = 1, bracketed by [0.75, 1.5]
        assertRootFound(solver, quintic, 0.75, 1.5, 1.0);

        // Root near x = -0.5, bracketed by [-0.9, -0.2]
        assertRootFound(solver, quintic, -0.9, -0.2, -0.5);
    }
}
