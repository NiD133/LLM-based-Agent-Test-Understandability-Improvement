package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.QuinticFunction;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class MullerSolverTest_testQuinticFunction {

    /** Upper bound on the number of solver iterations allowed per root search. */
    private static final int MAX_EVALUATIONS = 100;

    /** The quintic function whose roots are being located. */
    private final UnivariateFunction quintic = new QuinticFunction();

    /** Solver under test. */
    private final UnivariateSolver solver = new MullerSolver();

    /**
     * Test of solver for the quintic function.
     *
     * <p>The quintic has known roots at -0.5, 0.0 and 1.0. For each of these the
     * solver is given a bracketing interval and is expected to converge to the
     * root within the solver's configured accuracy.</p>
     */
    @Test
    public void testQuinticFunction() {
        assertRootFound(0.0, -0.4, 0.2);
        assertRootFound(1.0, 0.75, 1.5);
        assertRootFound(-0.5, -0.9, -0.2);
    }

    /**
     * Asserts that the solver locates the {@code expectedRoot} of the quintic
     * function within the interval [{@code min}, {@code max}].
     *
     * @param expectedRoot the known root the solver should converge to
     * @param min lower bound of the bracketing interval
     * @param max upper bound of the bracketing interval
     */
    private void assertRootFound(double expectedRoot, double min, double max) {
        double tolerance = JdkMath.max(
                solver.getAbsoluteAccuracy(),
                JdkMath.abs(expectedRoot * solver.getRelativeAccuracy()));
        double actualRoot = solver.solve(MAX_EVALUATIONS, quintic, min, max);
        Assert.assertEquals(expectedRoot, actualRoot, tolerance);
    }
}
