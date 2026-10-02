package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.QuinticFunction;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link MullerSolver} can locate the roots of the quintic
 * function {@link QuinticFunction}, whose real roots are -0.5, 0, 0.5, 1 and 2.
 */
public class MullerSolverTest_testQuinticFunction {

    /** Maximum number of function evaluations the solver may use. */
    private static final int MAX_EVALUATIONS = 100;

    /**
     * Each root should be found when the solver is given a bracketing interval
     * that contains exactly that root.
     */
    @Test
    public void testQuinticFunction() {
        final UnivariateFunction quintic = new QuinticFunction();
        final UnivariateSolver solver = new MullerSolver();

        // root at 0.0, bracketed by [-0.4, 0.2]
        assertRootFound(solver, quintic, -0.4, 0.2, 0.0);

        // root at 1.0, bracketed by [0.75, 1.5]
        assertRootFound(solver, quintic, 0.75, 1.5, 1.0);

        // root at -0.5, bracketed by [-0.9, -0.2]
        assertRootFound(solver, quintic, -0.9, -0.2, -0.5);
    }

    /**
     * Solves {@code function} on the interval {@code [min, max]} and asserts that
     * the returned root matches {@code expectedRoot} within the solver's accuracy.
     */
    private void assertRootFound(final UnivariateSolver solver,
                                 final UnivariateFunction function,
                                 final double min,
                                 final double max,
                                 final double expectedRoot) {
        final double tolerance = JdkMath.max(
                solver.getAbsoluteAccuracy(),
                JdkMath.abs(expectedRoot * solver.getRelativeAccuracy()));
        final double actualRoot = solver.solve(MAX_EVALUATIONS, function, min, max);
        Assert.assertEquals(expectedRoot, actualRoot, tolerance);
    }
}
