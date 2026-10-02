/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/**
 * Test case for {@link MullerSolver Muller} solver.
 * <p>
 * Muller's method converges almost quadratically near roots, but it can
 * be very slow in regions far away from zeros. Test runs show that for
 * reasonably good initial values, for a default absolute accuracy of 1E-6,
 * it generally takes 5 to 10 iterations for the solver to converge.
 * <p>
 * Tests for the exponential function illustrate the situations where
 * Muller solver performs poorly.
 */
public final class MullerSolverTest {

    /** Maximum number of iterations allowed for the solver in all tests. */
    private static final int MAX_EVAL = 100;

    /**
     * Computes the acceptable tolerance for a root-finding result.
     * <p>
     * The tolerance is the larger of the solver's absolute accuracy and
     * the product of the expected value's magnitude with the relative accuracy,
     * matching the convention used throughout the solver's own stopping criteria.
     *
     * @param solver   the configured solver whose accuracy settings are queried
     * @param expected the analytically known root value
     * @return the maximum allowed deviation between {@code expected} and the
     *         computed result
     */
    private static double toleranceFor(UnivariateSolver solver, double expected) {
        return JdkMath.max(
            solver.getAbsoluteAccuracy(),
            JdkMath.abs(expected * solver.getRelativeAccuracy()));
    }

    /**
     * Test of solver for the sine function.
     * <p>
     * Verifies two roots of sin(x): x = π in [3, 4] and x = 0 in [-1, 1.5].
     */
    @Test
    public void testSinFunction() {
        UnivariateFunction f = new Sin();
        UnivariateSolver solver = new MullerSolver();

        // Root at x = π within [3, 4]
        double expectedPiRoot = JdkMath.PI;
        double resultPiRoot = solver.solve(MAX_EVAL, f, 3.0, 4.0);
        Assert.assertEquals(expectedPiRoot, resultPiRoot, toleranceFor(solver, expectedPiRoot));

        // Root at x = 0 within [-1, 1.5]
        double expectedZeroRoot = 0.0;
        double resultZeroRoot = solver.solve(MAX_EVAL, f, -1.0, 1.5);
        Assert.assertEquals(expectedZeroRoot, resultZeroRoot, toleranceFor(solver, expectedZeroRoot));
    }

    /**
     * Test of solver for the quintic function.
     * <p>
     * The quintic function has three roots: x = 0, x = 1, and x = -0.5.
     * Each is found in a bracketing interval that isolates exactly one root.
     */
    @Test
    public void testQuinticFunction() {
        UnivariateFunction f = new QuinticFunction();
        UnivariateSolver solver = new MullerSolver();

        // Root at x = 0 within [-0.4, 0.2]
        double expectedRoot0 = 0.0;
        double resultRoot0 = solver.solve(MAX_EVAL, f, -0.4, 0.2);
        Assert.assertEquals(expectedRoot0, resultRoot0, toleranceFor(solver, expectedRoot0));

        // Root at x = 1 within [0.75, 1.5]
        double expectedRoot1 = 1.0;
        double resultRoot1 = solver.solve(MAX_EVAL, f, 0.75, 1.5);
        Assert.assertEquals(expectedRoot1, resultRoot1, toleranceFor(solver, expectedRoot1));

        // Root at x = -0.5 within [-0.9, -0.2]
        double expectedRootNeg05 = -0.5;
        double resultRootNeg05 = solver.solve(MAX_EVAL, f, -0.9, -0.2);
        Assert.assertEquals(expectedRootNeg05, resultRootNeg05, toleranceFor(solver, expectedRootNeg05));
    }

    /**
     * Test of solver for the expm1 function (e^x - 1).
     * <p>
     * The only root is at x = 0. Three increasingly wide bracketing intervals
     * are tested to show that the solver relies on bisection fallback for wide
     * intervals (convergence may take 10–15 iterations instead of the typical 5–10).
     */
    @Test
    public void testExpm1Function() {
        UnivariateFunction f = new Expm1();
        UnivariateSolver solver = new MullerSolver();

        double expectedRoot = 0.0;

        // Narrow interval: solver converges quickly
        double resultNarrow = solver.solve(MAX_EVAL, f, -1.0, 2.0);
        Assert.assertEquals(expectedRoot, resultNarrow, toleranceFor(solver, expectedRoot));

        // Medium interval: bisection fallback kicks in
        double resultMedium = solver.solve(MAX_EVAL, f, -20.0, 10.0);
        Assert.assertEquals(expectedRoot, resultMedium, toleranceFor(solver, expectedRoot));

        // Wide interval: bisection fallback is essential to stay within MAX_EVAL
        double resultWide = solver.solve(MAX_EVAL, f, -50.0, 100.0);
        Assert.assertEquals(expectedRoot, resultWide, toleranceFor(solver, expectedRoot));
    }

    /**
     * Test of invalid parameters for the solver.
     * <p>
     * Two invalid inputs are tested:
     * <ul>
     *   <li>A reversed interval [1, -1] where min &gt; max, which must throw
     *       {@link NumberIsTooLargeException}.</li>
     *   <li>A valid interval [2, 3] that does not bracket any root of sin(x),
     *       which must throw {@link NoBracketingException}.</li>
     * </ul>
     */
    @Test
    public void testParameters() {
        UnivariateFunction f = new Sin();
        UnivariateSolver solver = new MullerSolver();

        try {
            double root = solver.solve(MAX_EVAL, f, 1, -1);
            System.out.println("root=" + root);
            Assert.fail("Expecting NumberIsTooLargeException - bad interval");
        } catch (NumberIsTooLargeException ex) {
            // expected: min > max is not a valid bracketing interval
        }

        try {
            solver.solve(MAX_EVAL, f, 2, 3);
            Assert.fail("Expecting NoBracketingException - no bracketing");
        } catch (NoBracketingException ex) {
            // expected: sin(x) has no root in [2, 3]
        }
    }
}
