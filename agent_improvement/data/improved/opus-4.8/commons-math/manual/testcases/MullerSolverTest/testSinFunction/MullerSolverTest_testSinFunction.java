package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class MullerSolverTest_testSinFunction {

    /** Maximum number of function evaluations the solver is allowed to perform. */
    private static final int MAX_EVALUATIONS = 100;

    /**
     * Verifies that {@link MullerSolver} finds roots of the sine function on
     * intervals that bracket a known root.
     *
     * <p>Two roots are checked:</p>
     * <ul>
     *   <li>&pi;, bracketed by the interval [3.0, 4.0]</li>
     *   <li>0, bracketed by the interval [-1.0, 1.5]</li>
     * </ul>
     */
    @Test
    public void testSinFunction() {
        final UnivariateFunction sine = new Sin();
        final UnivariateSolver solver = new MullerSolver();

        // Root at pi, bracketed by [3.0, 4.0].
        assertRootFound(solver, sine, 3.0, 4.0, JdkMath.PI);

        // Root at 0, bracketed by [-1.0, 1.5].
        assertRootFound(solver, sine, -1.0, 1.5, 0.0);
    }

    /**
     * Solves {@code function} on the interval [{@code min}, {@code max}] and asserts
     * that the returned root matches {@code expectedRoot} within the solver's accuracy.
     */
    private void assertRootFound(UnivariateSolver solver, UnivariateFunction function,
                                 double min, double max, double expectedRoot) {
        double tolerance = JdkMath.max(
                solver.getAbsoluteAccuracy(),
                JdkMath.abs(expectedRoot * solver.getRelativeAccuracy()));

        double actualRoot = solver.solve(MAX_EVALUATIONS, function, min, max);

        Assert.assertEquals(expectedRoot, actualRoot, tolerance);
    }
}
