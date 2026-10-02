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
        UnivariateFunction quintic = new QuinticFunction();
        UnivariateSolver solver = new MullerSolver();

        assertRootEquals(0.0, solver, quintic, -0.4, 0.2);
        assertRootEquals(1.0, solver, quintic, 0.75, 1.5);
        assertRootEquals(-0.5, solver, quintic, -0.9, -0.2);
    }

    private void assertRootEquals(double expected,
                                  UnivariateSolver solver,
                                  UnivariateFunction function,
                                  double min,
                                  double max) {
        double tolerance = JdkMath.max(solver.getAbsoluteAccuracy(),
                                       JdkMath.abs(expected * solver.getRelativeAccuracy()));
        double result = solver.solve(MAX_EVALUATIONS, function, min, max);
        Assert.assertEquals(expected, result, tolerance);
    }
}
