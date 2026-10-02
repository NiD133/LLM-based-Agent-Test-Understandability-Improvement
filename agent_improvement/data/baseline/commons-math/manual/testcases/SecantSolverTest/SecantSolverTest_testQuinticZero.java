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

public class SecantSolverTest_testQuinticZero {

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
    public void testQuinticZero() {
        // The quintic function has zeros at 0, +-0.5 and +-1.
        // Around the root of 0 the function is well behaved, with a second
        // derivative of zero a 0.
        // The other roots are less well to find, in particular the root at 1,
        // because the function grows fast for x>1.
        // The function has extrema (first derivative is zero) at 0.27195613
        // and 0.82221643, intervals containing these values are harder for
        // the solvers.
        UnivariateFunction f = new QuinticFunction();
        double result;
        UnivariateSolver solver = getSolver();
        double atol = solver.getAbsoluteAccuracy();
        int[] counts = getQuinticEvalCounts();
        // Tests data: initial bounds, and expected solution, per test case.
        double[][] testsData = { { -0.2, 0.2, 0.0 }, { -0.1, 0.3, 0.0 }, { -0.3, 0.45, 0.0 }, { 0.3, 0.7, 0.5 }, { 0.2, 0.6, 0.5 }, { 0.05, 0.95, 0.5 }, { 0.85, 1.25, 1.0 }, { 0.8, 1.2, 1.0 }, { 0.85, 1.75, 1.0 }, { 0.55, 1.45, 1.0 }, { 0.85, 5.0, 1.0 } };
        int maxIter = 500;
        for (int i = 0; i < testsData.length; i++) {
            // Skip test, if needed.
            if (counts[i] == -1) {
                continue;
            }
            // Compute solution.
            double[] testData = testsData[i];
            result = solver.solve(maxIter, f, testData[0], testData[1]);
            //System.out.println(
            //    "Root: " + result + " Evaluations: " + solver.getEvaluations());
            // Check solution.
            Assert.assertEquals(result, testData[2], atol);
            Assert.assertTrue(solver.getEvaluations() <= counts[i] + 1);
        }
    }
}
