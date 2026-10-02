package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.QuinticFunction;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Expm1;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.legacy.exception.NoBracketingException;
import org.apache.commons.math4.legacy.exception.NumberIsTooLargeException;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class MullerSolverTest_testQuinticFunction {

    /**
     * Test of solver for the quintic function.
     */
    @Test
    public void testQuinticFunction() {
        UnivariateFunction f = new QuinticFunction();
        UnivariateSolver solver = new MullerSolver();

        // Root at 0.0, bracketed by [-0.4, 0.2]
        assertRootFound(solver, f, -0.4, 0.2, 0.0);

        // Root at 1.0, bracketed by [0.75, 1.5]
        assertRootFound(solver, f, 0.75, 1.5, 1.0);

        // Root at -0.5, bracketed by [-0.9, -0.2]
        assertRootFound(solver, f, -0.9, -0.2, -0.5);
    }

    /**
     * Solves for a root of {@code f} in [{@code min}, {@code max}] and asserts
     * that the result equals {@code expected} within the solver's accuracy.
     */
    private void assertRootFound(UnivariateSolver solver, UnivariateFunction f,
                                  double min, double max, double expected) {
        double tolerance = JdkMath.max(solver.getAbsoluteAccuracy(),
                                       JdkMath.abs(expected * solver.getRelativeAccuracy()));
        double result = solver.solve(100, f, min, max);
        Assert.assertEquals(expected, result, tolerance);
    }
}
