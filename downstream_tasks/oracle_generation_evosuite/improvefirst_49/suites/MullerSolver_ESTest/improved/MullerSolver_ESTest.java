/*
 * Improved version of the EvoSuite-generated test for MullerSolver.
 * Understandability enhancements: descriptive method names, explanatory comments,
 * and meaningful variable names. Runtime behaviour is identical to the original.
 */

package org.apache.commons.math4.legacy.analysis.solvers;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Cbrt;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.legacy.analysis.function.Ulp;
import org.apache.commons.math4.legacy.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math4.legacy.analysis.solvers.MullerSolver;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class MullerSolver_ESTest extends MullerSolver_ESTest_scaffolding {

    /**
     * The cube-root function cbrt(x) has its only root at x=0.
     * With a search interval that straddles zero (negative lower bound, huge positive
     * upper bound), the solver should converge to 0.0 within a generous tolerance.
     */
    @Test(timeout = 4000)
    public void test_solveCbrt_largeInterval_returnsZero() throws Throwable {
        MullerSolver solver = new MullerSolver();
        Cbrt cbrt = new Cbrt();

        double root = solver.solve(1392203, (UnivariateFunction) cbrt, -2825.0795928397, 2.7950664235456177E85);

        assertEquals(0.0, root, 0.01);
    }

    /**
     * When the search interval for sin(x) is entirely in positive territory and the
     * upper bound is astronomically large, the solver exhausts its evaluation budget
     * (2331 evaluations) without converging and must throw a RuntimeException.
     */
    @Test(timeout = 4000)
    public void test_solveSin_exceedsMaxEvaluations_throwsRuntimeException() throws Throwable {
        MullerSolver solver = new MullerSolver();
        Sin sin = new Sin();

        try {
            solver.solve(2331, (UnivariateFunction) sin, 3824.7097985564, 3.8581732071331E174);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // The solver should report that the maximum function-evaluation count was exceeded.
            verifyException("org.apache.commons.math4.legacy.analysis.solvers.BaseAbstractUnivariateSolver", e);
        }
    }

    /**
     * cbrt(x) has a root at x=0. With the interval [-1321.9, 2331] the solver
     * should find a value very close to zero (approximately 3.06e-7).
     */
    @Test(timeout = 4000)
    public void test_solveCbrt_intervalAroundZero_returnsNearZeroRoot() throws Throwable {
        MullerSolver solver = new MullerSolver();
        Cbrt cbrt = new Cbrt();

        double root = solver.solve(2331, (UnivariateFunction) cbrt, -1321.9, (double) 2331);

        assertEquals(3.060102951752152E-7, root, 0.01);
    }

    /**
     * A PolynomialFunction whose coefficient array is all zeros represents the
     * zero polynomial (f(x) = 0 everywhere). Asking the solver for a root of such
     * a function starting near the given point should yield NaN, because every
     * point is trivially a root and no unique solution exists.
     */
    @Test(timeout = 4000)
    public void test_solveZeroPolynomial_returnsNaN() throws Throwable {
        MullerSolver solver = new MullerSolver(1.0E-15);
        // Coefficients [0, 0, 0, 0] → f(x) = 0 for all x
        double[] zeroCoefficients = new double[4];
        PolynomialFunction zeroPolynomial = new PolynomialFunction(zeroCoefficients);

        double result = solver.solve(5575, (UnivariateFunction) zeroPolynomial, 0.0016310069148432287);

        assertEquals(Double.NaN, result, 0.01);
    }

    /**
     * When the upper bound of the search interval is a positive number extremely
     * close to zero (4.8e-220), cbrt evaluated there is also effectively zero.
     * The solver should identify this endpoint as the root and return it unchanged.
     */
    @Test(timeout = 4000)
    public void test_solveCbrt_upperBoundNearZero_returnsUpperBound() throws Throwable {
        MullerSolver solver = new MullerSolver();
        Cbrt cbrt = new Cbrt();
        double upperBound = 4.800501435803201E-220;

        double root = solver.solve(2331, (UnivariateFunction) cbrt, -1476.2544696353405, upperBound);

        assertEquals(upperBound, root, 0.01);
    }

    /**
     * With a solver configured using explicit absolute and relative tolerances,
     * solving the ulp(x) function starting near 1.59 should return a result
     * essentially equal to the starting point itself.
     */
    @Test(timeout = 4000)
    public void test_solveUlp_customTolerances_returnsStartingPoint() throws Throwable {
        MullerSolver solver = new MullerSolver(3.1660099222737955E-7, -4620.5448992389);
        Ulp ulp = new Ulp();
        double startingPoint = 1.5902118682861328;

        double result = solver.solve(2048, (UnivariateFunction) ulp, startingPoint);

        assertEquals(startingPoint, result, 0.01);
    }
}
