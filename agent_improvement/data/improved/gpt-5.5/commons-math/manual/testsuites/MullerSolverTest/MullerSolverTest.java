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

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.apache.commons.math4.legacy.analysis.QuinticFunction;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Expm1;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.legacy.exception.NoBracketingException;
import org.apache.commons.math4.legacy.exception.NumberIsTooLargeException;
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
    private static final int MAX_EVALUATIONS = 100;

    /**
     * Test of solver for the sine function.
     */
    @Test
    public void testSinFunction() {
        UnivariateFunction function = new Sin();
        UnivariateSolver solver = new MullerSolver();

        assertRoot(solver, function, 3.0, 4.0, JdkMath.PI);
        assertRoot(solver, function, -1.0, 1.5, 0.0);
    }

    /**
     * Test of solver for the quintic function.
     */
    @Test
    public void testQuinticFunction() {
        UnivariateFunction function = new QuinticFunction();
        UnivariateSolver solver = new MullerSolver();

        assertRoot(solver, function, -0.4, 0.2, 0.0);
        assertRoot(solver, function, 0.75, 1.5, 1.0);
        assertRoot(solver, function, -0.9, -0.2, -0.5);
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
        UnivariateFunction function = new Expm1();
        UnivariateSolver solver = new MullerSolver();

        assertRoot(solver, function, -1.0, 2.0, 0.0);
        assertRoot(solver, function, -20.0, 10.0, 0.0);
        assertRoot(solver, function, -50.0, 100.0, 0.0);
    }

    /**
     * Test of parameters for the solver.
     */
    @Test
    public void testParameters() {
        UnivariateFunction function = new Sin();
        UnivariateSolver solver = new MullerSolver();

        assertBadIntervalIsRejected(solver, function);
        assertUnbracketedIntervalIsRejected(solver, function);
    }

    private static void assertRoot(UnivariateSolver solver,
                                   UnivariateFunction function,
                                   double min,
                                   double max,
                                   double expected) {
        double tolerance = JdkMath.max(solver.getAbsoluteAccuracy(),
                                       JdkMath.abs(expected * solver.getRelativeAccuracy()));
        double result = solver.solve(MAX_EVALUATIONS, function, min, max);
        Assert.assertEquals(expected, result, tolerance);
    }

    private static void assertBadIntervalIsRejected(UnivariateSolver solver,
                                                    UnivariateFunction function) {
        try {
            double root = solver.solve(100, function, 1, -1);
            System.out.println("root=" + root);
            Assert.fail("Expecting NumberIsTooLargeException - bad interval");
        } catch (NumberIsTooLargeException ex) {
            // expected
        }
    }

    private static void assertUnbracketedIntervalIsRejected(UnivariateSolver solver,
                                                            UnivariateFunction function) {
        try {
            solver.solve(100, function, 2, 3);
            Assert.fail("Expecting NoBracketingException - no bracketing");
        } catch (NoBracketingException ex) {
            // expected
        }
    }
}
