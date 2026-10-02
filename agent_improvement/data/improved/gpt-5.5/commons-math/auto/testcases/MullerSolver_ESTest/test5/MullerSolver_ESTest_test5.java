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
public class MullerSolver_ESTest_test5 extends MullerSolver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        final double relativeAccuracy = 3.1660099222737955E-7;
        final double absoluteAccuracy = -4620.5448992389;
        final int maxEvaluations = 2048;
        final double startValue = 1.5902118682861328;
        final double assertionTolerance = 0.01;

        MullerSolver solver = new MullerSolver(relativeAccuracy, absoluteAccuracy);
        Ulp ulpFunction = new Ulp();

        double root = solver.solve(maxEvaluations, (UnivariateFunction) ulpFunction, startValue);

        assertEquals(startValue, root, assertionTolerance);
    }
}
