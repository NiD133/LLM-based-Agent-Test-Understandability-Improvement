package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.legacy.exception.NoBracketingException;
import org.apache.commons.math4.legacy.exception.NumberIsTooLargeException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that MullerSolver correctly validates its input parameters before attempting to find a root.
 * Specifically, it must reject an inverted interval (min > max) and an interval that does not
 * bracket a root (f(min) and f(max) have the same sign).
 */
public class MullerSolverTest_testParameters {

    private static final int MAX_EVALUATIONS = 100;

    @Test
    public void testParameters() {
        UnivariateFunction sinFunction = new Sin();
        UnivariateSolver solver = new MullerSolver();

        // An interval [1, -1] is inverted (lower bound > upper bound), which is illegal.
        // MullerSolver must throw NumberIsTooLargeException in this case.
        try {
            double invertedMin = 1;
            double invertedMax = -1;
            double root = solver.solve(MAX_EVALUATIONS, sinFunction, invertedMin, invertedMax);
            System.out.println("root=" + root);
            Assert.fail("Expecting NumberIsTooLargeException - bad interval");
        } catch (NumberIsTooLargeException ex) {
            // expected: solver correctly rejected the inverted interval
        }

        // The interval [2, 3] lies entirely within (π/2, π), where sin is positive throughout,
        // so it does not bracket a root. MullerSolver must throw NoBracketingException.
        try {
            double nonBracketingMin = 2;
            double nonBracketingMax = 3;
            solver.solve(MAX_EVALUATIONS, sinFunction, nonBracketingMin, nonBracketingMax);
            Assert.fail("Expecting NoBracketingException - no bracketing");
        } catch (NoBracketingException ex) {
            // expected: solver correctly detected the absence of a sign change
        }
    }
}
