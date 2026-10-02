package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.legacy.exception.NoBracketingException;
import org.apache.commons.math4.legacy.exception.NumberIsTooLargeException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that {@link MullerSolver} rejects invalid solve parameters by throwing
 * the appropriate exception instead of returning a result.
 */
public class MullerSolverTest_testParameters {

    /** Upper bound on the number of evaluations the solver may perform. */
    private static final int MAX_EVALUATIONS = 100;

    /**
     * The solver should reject an interval whose lower bound is greater than
     * its upper bound, and an interval over which the function does not change
     * sign (so no root is bracketed).
     */
    @Test
    public void testParameters() {
        final UnivariateFunction sine = new Sin();
        final UnivariateSolver solver = new MullerSolver();

        // An inverted interval [min, max] = [1, -1] is not a valid search range.
        try {
            solver.solve(MAX_EVALUATIONS, sine, 1, -1);
            Assert.fail("Expecting NumberIsTooLargeException - bad interval");
        } catch (NumberIsTooLargeException expected) {
            // sin has no root in an inverted interval; the solver rejects it.
        }

        // sin(x) keeps the same sign over [2, 3], so no root is bracketed.
        try {
            solver.solve(MAX_EVALUATIONS, sine, 2, 3);
            Assert.fail("Expecting NoBracketingException - no bracketing");
        } catch (NoBracketingException expected) {
            // No sign change means the solver cannot bracket a root.
        }
    }
}
