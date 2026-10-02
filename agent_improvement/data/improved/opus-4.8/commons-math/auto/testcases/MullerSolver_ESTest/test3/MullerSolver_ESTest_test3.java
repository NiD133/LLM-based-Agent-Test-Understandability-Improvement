package org.apache.commons.math4.legacy.analysis.solvers;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Cbrt;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.legacy.analysis.function.Ulp;
import org.apache.commons.math4.legacy.analysis.polynomials.PolynomialFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MullerSolver_ESTest_test3 extends MullerSolver_ESTest_scaffolding {

    /**
     * Solving the constant-zero polynomial (every coefficient is 0) starting from a
     * single point makes Muller's method unable to converge, so the solver returns NaN.
     */
    @Test(timeout = 4000)
    public void solveZeroPolynomialFromStartValueReturnsNaN() throws Throwable {
        double absoluteAccuracy = 1.0E-15;
        MullerSolver mullerSolver = new MullerSolver(absoluteAccuracy);

        // All coefficients default to 0.0, giving the constant function f(x) = 0.
        double[] zeroCoefficients = new double[4];
        PolynomialFunction zeroPolynomial = new PolynomialFunction(zeroCoefficients);

        int maxEvaluations = 5575;
        double startValue = 0.0016310069148432287;
        double root = mullerSolver.solve(maxEvaluations, (UnivariateFunction) zeroPolynomial, startValue);

        assertEquals(Double.NaN, root, 0.01);
    }
}
