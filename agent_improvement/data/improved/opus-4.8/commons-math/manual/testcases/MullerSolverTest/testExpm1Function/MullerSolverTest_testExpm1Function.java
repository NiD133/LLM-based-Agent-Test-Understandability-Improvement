package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Expm1;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class MullerSolverTest_testExpm1Function {

    /** Maximum number of solver evaluations allowed per call. */
    private static final int MAX_EVALUATIONS = 100;

    /** The only root of expm1(x) = e^x - 1 is x = 0. */
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
        // The solver must find the root x = 0 regardless of how wide the
        // bracketing interval [min, max] around it is.
        assertFindsRootOfExpm1(-1.0, 2.0);
        assertFindsRootOfExpm1(-20.0, 10.0);
        assertFindsRootOfExpm1(-50.0, 100.0);
    }

    /**
     * Solves expm1(x) = 0 over the interval [min, max] and asserts that the
     * solver converges to the expected root within the solver's accuracy.
     */
    private void assertFindsRootOfExpm1(double min, double max) {
        UnivariateFunction f = new Expm1();
        UnivariateSolver solver = new MullerSolver();

        double tolerance = JdkMath.max(
                solver.getAbsoluteAccuracy(),
                JdkMath.abs(EXPECTED_ROOT * solver.getRelativeAccuracy()));

        double result = solver.solve(MAX_EVALUATIONS, f, min, max);

        Assert.assertEquals(EXPECTED_ROOT, result, tolerance);
    }
}
