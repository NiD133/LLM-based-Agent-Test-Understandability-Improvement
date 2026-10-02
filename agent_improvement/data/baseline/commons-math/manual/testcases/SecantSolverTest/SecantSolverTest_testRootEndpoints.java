package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.QuinticFunction;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.XMinus5Function;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.legacy.exception.NoBracketingException;
import org.apache.commons.math4.legacy.exception.NumberIsTooLargeException;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class SecantSolverTest_testRootEndpoints {

    /**
     * {@inheritDoc}
     */
    protected UnivariateSolver getSolver() {
        return new SecantSolver();
    }

    /**
     * {@inheritDoc}
     */
    protected int[] getQuinticEvalCounts() {
        // As the Secant method does not maintain a bracketed solution,
        // convergence is not guaranteed. Two test cases are disabled (-1) due
        // to bad solutions.
        return new int[] { 3, 7, -1, 8, 9, 8, 11, 12, 14, -1, 16 };
    }

    /**
     * Returns the solver to use to perform the tests.
     * @return the solver to use to perform the tests
     */
    private UnivariateSolver __super_getSolver();

    /**
     * Returns the expected number of evaluations for the
     * {@link #testQuinticZero} unit test. A value of {@code -1} indicates that
     * the test should be skipped for that solver.
     * @return the expected number of evaluations for the
     * {@link #testQuinticZero} unit test
     */
    private int[] __super_getQuinticEvalCounts();

    private double getSolution(UnivariateSolver solver, int maxEval, UnivariateFunction f, double left, double right, AllowedSolution allowedSolution) {
        try {
            @SuppressWarnings("unchecked")
            BracketedUnivariateSolver<UnivariateFunction> bracketing = (BracketedUnivariateSolver<UnivariateFunction>) solver;
            return bracketing.solve(100, f, left, right, allowedSolution);
        } catch (ClassCastException cce) {
            double baseRoot = solver.solve(maxEval, f, left, right);
            if (baseRoot <= left || baseRoot >= right) {
                // the solution slipped out of interval
                return Double.NaN;
            }
            PegasusSolver bracketing = new PegasusSolver(solver.getRelativeAccuracy(), solver.getAbsoluteAccuracy(), solver.getFunctionValueAccuracy());
            return UnivariateSolverUtils.forceSide(maxEval - solver.getEvaluations(), f, bracketing, baseRoot, left, right, allowedSolution);
        }
    }

    @Test
    public void testRootEndpoints() {
        UnivariateFunction f = new XMinus5Function();
        UnivariateSolver solver = getSolver();
        // End-point is root. This should be a special case in the solver, and
        // the initial end-point should be returned exactly.
        double result = solver.solve(100, f, 5.0, 6.0);
        Assert.assertEquals(5.0, result, 0.0);
        result = solver.solve(100, f, 4.0, 5.0);
        Assert.assertEquals(5.0, result, 0.0);
        result = solver.solve(100, f, 5.0, 6.0, 5.5);
        Assert.assertEquals(5.0, result, 0.0);
        result = solver.solve(100, f, 4.0, 5.0, 4.5);
        Assert.assertEquals(5.0, result, 0.0);
    }
}
