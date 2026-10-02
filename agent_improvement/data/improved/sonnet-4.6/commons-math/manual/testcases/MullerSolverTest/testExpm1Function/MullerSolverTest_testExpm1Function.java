package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Expm1;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that {@link MullerSolver} correctly finds the root of the expm1 function
 * (i.e., e^x - 1 = 0, whose only real root is x = 0) across search intervals of
 * increasing width. Wide intervals require bisection fallback to converge within
 * the 100-iteration budget.
 */
public class MullerSolverTest_testExpm1Function {

    private static final int    MAX_ITERATIONS = 100;
    private static final double EXPECTED_ROOT  = 0.0;

    @Test
    public void testExpm1Function() {
        UnivariateFunction expm1  = new Expm1();
        UnivariateSolver   solver = new MullerSolver();

        // Narrow interval: converges quickly
        assertRootFound(solver, expm1, -1.0, 2.0);

        // Medium interval: may need several iterations
        assertRootFound(solver, expm1, -20.0, 10.0);

        // Wide interval: requires bisection fallback to stay within MAX_ITERATIONS
        assertRootFound(solver, expm1, -50.0, 100.0);
    }

    /**
     * Verifies that {@code solver} finds the expected root (x = 0) of {@code f}
     * within [{@code min}, {@code max}] using at most {@value #MAX_ITERATIONS} evaluations.
     */
    private void assertRootFound(UnivariateSolver solver, UnivariateFunction f,
                                 double min, double max) {
        double tolerance = JdkMath.max(
                solver.getAbsoluteAccuracy(),
                JdkMath.abs(EXPECTED_ROOT * solver.getRelativeAccuracy()));

        double result = solver.solve(MAX_ITERATIONS, f, min, max);

        Assert.assertEquals(
                String.format("Root of expm1 on [%.1f, %.1f]", min, max),
                EXPECTED_ROOT, result, tolerance);
    }
}
