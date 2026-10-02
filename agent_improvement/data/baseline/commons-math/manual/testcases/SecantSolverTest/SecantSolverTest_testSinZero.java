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

public class SecantSolverTest_testSinZero {

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
    public void testSinZero() {
        // The sinus function is behaved well around the root at pi. The second
        // order derivative is zero, which means linear approximating methods
        // still converge quadratically.
        UnivariateFunction f = new Sin();
        double result;
        UnivariateSolver solver = getSolver();
        result = solver.solve(100, f, 3, 4);
        //System.out.println(
        //    "Root: " + result + " Evaluations: " + solver.getEvaluations());
        Assert.assertEquals(result, JdkMath.PI, solver.getAbsoluteAccuracy());
        Assert.assertTrue(solver.getEvaluations() <= 6);
        result = solver.solve(100, f, 1, 4);
        //System.out.println(
        //    "Root: " + result + " Evaluations: " + solver.getEvaluations());
        Assert.assertEquals(result, JdkMath.PI, solver.getAbsoluteAccuracy());
        Assert.assertTrue(solver.getEvaluations() <= 7);
    }
}
