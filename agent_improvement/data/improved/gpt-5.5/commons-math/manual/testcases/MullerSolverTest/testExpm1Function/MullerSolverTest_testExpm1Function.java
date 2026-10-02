package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Expm1;
import org.junit.Assert;
import org.junit.Test;

public class MullerSolverTest_testExpm1Function {

    private static final int MAX_EVALUATIONS = 100;
    private static final double EXPECTED_ROOT = 0.0;

    /**
     * Test of solver for the exponential function.
     * <p>
     * It takes 10 to 15 iterations for the last two tests to converge.
     * In fact, if not for the bisection alternative, the solver would
     * exceed the default maximal iteration of 100.
     */
    @Test
    public void testExpm1Function() {
        UnivariateFunction function = new Expm1();
        UnivariateSolver solver = new MullerSolver();

        assertRootFoundInInterval(solver, function, -1.0, 2.0, EXPECTED_ROOT);
        assertRootFoundInInterval(solver, function, -20.0, 10.0, EXPECTED_ROOT);
        assertRootFoundInInterval(solver, function, -50.0, 100.0, EXPECTED_ROOT);
    }

    private void assertRootFoundInInterval(UnivariateSolver solver,
                                           UnivariateFunction function,
                                           double min,
                                           double max,
                                           double expected) {
        double tolerance = JdkMath.max(
                solver.getAbsoluteAccuracy(),
                JdkMath.abs(expected * solver.getRelativeAccuracy()));
        double result = solver.solve(MAX_EVALUATIONS, function, min, max);

        Assert.assertEquals(expected, result, tolerance);
    }
}
