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

public class MullerSolverTest_testSinFunction {

    /**
     * Test of solver for the sine function.
     */
    @Test
    public void testSinFunction() {
        UnivariateFunction f = new Sin();
        UnivariateSolver solver = new MullerSolver();
        double min;
        double max;
        double expected;
        double result;
        double tolerance;
        min = 3.0;
        max = 4.0;
        expected = JdkMath.PI;
        tolerance = JdkMath.max(solver.getAbsoluteAccuracy(), JdkMath.abs(expected * solver.getRelativeAccuracy()));
        result = solver.solve(100, f, min, max);
        Assert.assertEquals(expected, result, tolerance);
        min = -1.0;
        max = 1.5;
        expected = 0.0;
        tolerance = JdkMath.max(solver.getAbsoluteAccuracy(), JdkMath.abs(expected * solver.getRelativeAccuracy()));
        result = solver.solve(100, f, min, max);
        Assert.assertEquals(expected, result, tolerance);
    }
}
