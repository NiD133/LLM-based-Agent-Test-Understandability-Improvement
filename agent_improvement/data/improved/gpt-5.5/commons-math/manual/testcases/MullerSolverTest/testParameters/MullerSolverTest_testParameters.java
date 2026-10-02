package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.legacy.exception.NoBracketingException;
import org.apache.commons.math4.legacy.exception.NumberIsTooLargeException;
import org.junit.Assert;
import org.junit.Test;

public class MullerSolverTest_testParameters {

    /**
     * Verifies that invalid intervals are rejected before MullerSolver attempts
     * to compute a root.
     */
    @Test
    public void testParameters() {
        UnivariateFunction function = new Sin();
        UnivariateSolver solver = new MullerSolver();

        assertBadIntervalIsRejected(solver, function);
        assertNonBracketingIntervalIsRejected(solver, function);
    }

    private void assertBadIntervalIsRejected(UnivariateSolver solver,
                                             UnivariateFunction function) {
        try {
            solver.solve(100, function, 1, -1);
            Assert.fail("Expecting NumberIsTooLargeException - bad interval");
        } catch (NumberIsTooLargeException ex) {
            // expected
        }
    }

    private void assertNonBracketingIntervalIsRejected(UnivariateSolver solver,
                                                       UnivariateFunction function) {
        try {
            solver.solve(100, function, 2, 3);
            Assert.fail("Expecting NoBracketingException - no bracketing");
        } catch (NoBracketingException ex) {
            // expected
        }
    }
}
