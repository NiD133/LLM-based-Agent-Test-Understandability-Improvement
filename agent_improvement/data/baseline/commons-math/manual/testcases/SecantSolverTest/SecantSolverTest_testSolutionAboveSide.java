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

public class SecantSolverTest_testSolutionAboveSide {

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
    public void testSolutionAboveSide() {
        UnivariateFunction f = new Sin();
        UnivariateSolver solver = getSolver();
        double left = -1.5;
        double right = 0.05;
        for (int i = 0; i < 10; i++) {
            // Test whether the allowed solutions are taken into account.
            double solution = getSolution(solver, 100, f, left, right, AllowedSolution.ABOVE_SIDE);
            if (!Double.isNaN(solution)) {
                Assert.assertTrue(f.value(solution) >= 0.0);
            }
            // Prepare for next test.
            left -= 0.1;
            right += 0.3;
        }
    }
}
