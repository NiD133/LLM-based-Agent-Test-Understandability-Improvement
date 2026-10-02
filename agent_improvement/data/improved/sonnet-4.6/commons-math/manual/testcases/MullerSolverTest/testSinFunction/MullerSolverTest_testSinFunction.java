package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class MullerSolverTest_testSinFunction {

    private static final int MAX_EVAL = 100;

    /**
     * Test that MullerSolver correctly finds roots of sin(x).
     *
     * sin(x) has roots at every multiple of PI. Two cases are verified:
     *   1. The root at PI, bracketed by [3.0, 4.0].
     *   2. The root at 0.0, bracketed by [-1.0, 1.5].
     *
     * The tolerance for each assertion is computed from the solver's own
     * accuracy settings, matching the pattern used in the original test suite.
     */
    @Test
    public void testSinFunction() {
        UnivariateFunction sinFunction = new Sin();
        UnivariateSolver solver = new MullerSolver();

        // --- Case 1: root at PI, bracket [3.0, 4.0] ---
        double min1 = 3.0;
        double max1 = 4.0;
        double expected1 = JdkMath.PI;
        double tolerance1 = JdkMath.max(
                solver.getAbsoluteAccuracy(),
                JdkMath.abs(expected1 * solver.getRelativeAccuracy()));
        double result1 = solver.solve(MAX_EVAL, sinFunction, min1, max1);
        Assert.assertEquals(expected1, result1, tolerance1);

        // --- Case 2: root at 0, bracket [-1.0, 1.5] ---
        double min2 = -1.0;
        double max2 = 1.5;
        double expected2 = 0.0;
        double tolerance2 = JdkMath.max(
                solver.getAbsoluteAccuracy(),
                JdkMath.abs(expected2 * solver.getRelativeAccuracy()));
        double result2 = solver.solve(MAX_EVAL, sinFunction, min2, max2);
        Assert.assertEquals(expected2, result2, tolerance2);
    }
}
