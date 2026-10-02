package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.apache.commons.math4.legacy.analysis.QuinticFunction;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.junit.Assert;
import org.junit.Test;

public class MullerSolverTest_testQuinticFunction {

    private static final int MAX_EVALUATIONS = 100;

    /**
     * Test of solver for the quintic function.
     */
    @Test
    public void testQuinticFunction() {
        UnivariateFunction function = new QuinticFunction();
        UnivariateSolver solver = new MullerSolver();

        assertQuinticRoot(solver, function, -0.4, 0.2, 0.0);
        assertQuinticRoot(solver, function, 0.75, 1.5, 1.0);
        assertQuinticRoot(solver, function, -0.9, -0.2, -0.5);
    }

    private void assertQuinticRoot(UnivariateSolver solver,
                                   UnivariateFunction function,
                                   double min,
                                   double max,
                                   double expected) {
        double tolerance = JdkMath.max(solver.getAbsoluteAccuracy(),
                JdkMath.abs(expected * solver.getRelativeAccuracy()));
        double result = solver.solve(MAX_EVALUATIONS, function, min, max);

        Assert.assertEquals(expected, result, tolerance);
    }
}
