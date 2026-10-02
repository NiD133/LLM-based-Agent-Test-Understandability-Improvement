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

    private static final int MAX_EVALUATIONS = 5575;
    private static final double INITIAL_GUESS = 0.0016310069148432287;
    private static final double ASSERTION_TOLERANCE = 0.01;

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        MullerSolver solver = new MullerSolver(1.0E-15);
        double[] zeroPolynomialCoefficients = new double[4];
        PolynomialFunction zeroPolynomial = new PolynomialFunction(zeroPolynomialCoefficients);

        double actualRoot = solver.solve(MAX_EVALUATIONS, (UnivariateFunction) zeroPolynomial, INITIAL_GUESS);

        assertEquals(Double.NaN, actualRoot, ASSERTION_TOLERANCE);
    }
}
