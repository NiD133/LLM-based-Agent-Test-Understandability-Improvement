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

public class MullerSolverTest_testExpm1Function {

    /**
     * Test of solver for the exponential function.
     * <p>
     * It takes 10 to 15 iterations for the last two tests to converge.
     * In fact, if not for the bisection alternative, the solver would
     * exceed the default maximal iteration of 100.
     */
    @Test
    public void testExpm1Function() {
        UnivariateFunction f = new Expm1();
        UnivariateSolver solver = new MullerSolver();
        double min;
        double max;
        double expected;
        double result;
        double tolerance;
        min = -1.0;
        max = 2.0;
        expected = 0.0;
        tolerance = JdkMath.max(solver.getAbsoluteAccuracy(), JdkMath.abs(expected * solver.getRelativeAccuracy()));
        result = solver.solve(100, f, min, max);
        Assert.assertEquals(expected, result, tolerance);
        min = -20.0;
        max = 10.0;
        expected = 0.0;
        tolerance = JdkMath.max(solver.getAbsoluteAccuracy(), JdkMath.abs(expected * solver.getRelativeAccuracy()));
        result = solver.solve(100, f, min, max);
        Assert.assertEquals(expected, result, tolerance);
        min = -50.0;
        max = 100.0;
        expected = 0.0;
        tolerance = JdkMath.max(solver.getAbsoluteAccuracy(), JdkMath.abs(expected * solver.getRelativeAccuracy()));
        result = solver.solve(100, f, min, max);
        Assert.assertEquals(expected, result, tolerance);
    }
}
