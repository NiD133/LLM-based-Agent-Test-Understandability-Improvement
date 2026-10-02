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
public class MullerSolver_ESTest_test4 extends MullerSolver_ESTest_scaffolding {

    private static final int MAX_EVALUATIONS = 2331;
    private static final double MIN = -1476.2544696353405;
    private static final double MAX = 4.800501435803201E-220;
    private static final double ASSERTION_TOLERANCE = 0.01;

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        MullerSolver solver = new MullerSolver();
        UnivariateFunction cbrtFunction = new Cbrt();

        double root = solver.solve(MAX_EVALUATIONS, cbrtFunction, MIN, MAX);

        assertEquals(MAX, root, ASSERTION_TOLERANCE);
    }
}
