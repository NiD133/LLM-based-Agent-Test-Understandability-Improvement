package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.junit.Assert;
import org.junit.Test;

public class MullerSolverTest_testSinFunction {

    /**
     * Test of solver for the sine function.
     */
    @Test
    public void testSinFunction() {
        UnivariateFunction sineFunction = new Sin();
        UnivariateSolver solver = new MullerSolver();

        assertSolverFindsRoot(solver, sineFunction, 3.0, 4.0, JdkMath.PI);
        assertSolverFindsRoot(solver, sineFunction, -1.0, 1.5, 0.0);
    }

    private void assertSolverFindsRoot(UnivariateSolver solver,
                                       UnivariateFunction function,
                                       double min,
                                       double max,
                                       double expectedRoot) {
        double tolerance = JdkMath.max(solver.getAbsoluteAccuracy(),
                JdkMath.abs(expectedRoot * solver.getRelativeAccuracy()));
        double actualRoot = solver.solve(100, function, min, max);

        Assert.assertEquals(expectedRoot, actualRoot, tolerance);
    }
}
