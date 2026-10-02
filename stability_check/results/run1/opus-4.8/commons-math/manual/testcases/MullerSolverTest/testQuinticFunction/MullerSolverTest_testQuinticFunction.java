package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.QuinticFunction;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class MullerSolverTest_testQuinticFunction {

    /** Maximum number of function evaluations allowed for each solve call. */
    private static final int MAX_EVALUATIONS = 100;

    private final UnivariateFunction quinticFunction = new QuinticFunction();
    private final UnivariateSolver solver = new MullerSolver();

    /**
     * Verifies that the Muller solver finds each known root of the quintic
     * function when given a bracketing interval around it.
     */
    @Test
    public void testQuinticFunction() {
        assertRootFoundInInterval(-0.4, 0.2, 0.0);
        assertRootFoundInInterval(0.75, 1.5, 1.0);
        assertRootFoundInInterval(-0.9, -0.2, -0.5);
    }

    /**
     * Solves the quintic function on the bracketing interval [min, max] and
     * asserts that the returned root matches the expected value within the
     * solver's configured accuracy.
     *
     * @param min          lower bound of the bracketing interval
     * @param max          upper bound of the bracketing interval
     * @param expectedRoot the root expected to lie inside the interval
     */
    private void assertRootFoundInInterval(double min, double max, double expectedRoot) {
        double tolerance = JdkMath.max(
                solver.getAbsoluteAccuracy(),
                JdkMath.abs(expectedRoot * solver.getRelativeAccuracy()));
        double actualRoot = solver.solve(MAX_EVALUATIONS, quinticFunction, min, max);
        Assert.assertEquals(expectedRoot, actualRoot, tolerance);
    }
}
