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
 *
 */
public final class MullerSolverTest {

    /** Maximum number of solver evaluations allowed per root search. */
    private static final int MAX_EVAL = 100;

    /**
     * Searches for a root of {@code f} on the bracketing interval
     * {@code [min, max]} and asserts that the value found matches
     * {@code expectedRoot}.
     * <p>
     * The tolerance combines the solver's absolute and relative accuracies,
     * exactly as recommended for the configured solver.
     *
     * @param solver       solver under test
     * @param f            function whose root is sought
     * @param min          lower bound of the search interval
     * @param max          upper bound of the search interval
     * @param expectedRoot the root expected within {@code [min, max]}
     */
    private void assertRootFound(UnivariateSolver solver, UnivariateFunction f,
                                 double min, double max, double expectedRoot) {
        final double tolerance = JdkMath.max(solver.getAbsoluteAccuracy(),
                JdkMath.abs(expectedRoot * solver.getRelativeAccuracy()));
        final double actualRoot = solver.solve(MAX_EVAL, f, min, max);
        Assert.assertEquals(expectedRoot, actualRoot, tolerance);
    }

    /**
     * Test of solver for the sine function.
     */
    @Test
    public void testSinFunction() {
        final UnivariateFunction f = new Sin();
        final UnivariateSolver solver = new MullerSolver();

        // Root at pi, bracketed by [3, 4].
        assertRootFound(solver, f, 3.0, 4.0, JdkMath.PI);
        // Root at 0, bracketed by [-1, 1.5].
        assertRootFound(solver, f, -1.0, 1.5, 0.0);
    }

    /**
     * Test of solver for the quintic function.
     */
    @Test
    public void testQuinticFunction() {
        final UnivariateFunction f = new QuinticFunction();
        final UnivariateSolver solver = new MullerSolver();

        assertRootFound(solver, f, -0.4, 0.2, 0.0);
        assertRootFound(solver, f, 0.75, 1.5, 1.0);
        assertRootFound(solver, f, -0.9, -0.2, -0.5);
    }

    /**
     * Test of solver for the exponential function.
     * <p>
     * It takes 10 to 15 iterations for the last two tests to converge.
     * In fact, if not for the bisection alternative, the solver would
     * exceed the default maximal iteration of 100.
     */
    @Test
    public void testExpm1Function() {
        final UnivariateFunction f = new Expm1();
        final UnivariateSolver solver = new MullerSolver();

        // The single root of expm1 is 0; each interval brackets it more loosely.
        assertRootFound(solver, f, -1.0, 2.0, 0.0);
        assertRootFound(solver, f, -20.0, 10.0, 0.0);
        assertRootFound(solver, f, -50.0, 100.0, 0.0);
    }

    /**
     * Test of parameters for the solver.
     */
    @Test
    public void testParameters() {
        final UnivariateFunction f = new Sin();
        final UnivariateSolver solver = new MullerSolver();

        try {
            // Lower bound greater than upper bound: invalid interval.
            final double root = solver.solve(MAX_EVAL, f, 1, -1);
            System.out.println("root=" + root);
            Assert.fail("Expecting NumberIsTooLargeException - bad interval");
        } catch (NumberIsTooLargeException ex) {
            // expected
        }
        try {
            // f has the same sign at both ends: no bracketed root.
            solver.solve(MAX_EVAL, f, 2, 3);
            Assert.fail("Expecting NoBracketingException - no bracketing");
        } catch (NoBracketingException ex) {
            // expected
        }
    }
}
