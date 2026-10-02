package org.apache.commons.math4.legacy.analysis.solvers;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Cbrt;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MullerSolver_ESTest_test4 extends MullerSolver_ESTest_scaffolding {

    /**
     * Solves the cube-root function over a bracketing interval whose upper bound
     * is effectively zero (4.8E-220). The cube root has its single real root at
     * x = 0, so the solver converges to the near-zero upper endpoint, which is
     * returned within the requested tolerance.
     */
    @Test(timeout = 4000)
    public void testSolveCubeRootReturnsNearZeroRoot() throws Throwable {
        MullerSolver solver = new MullerSolver();
        Cbrt cubeRoot = new Cbrt();

        int maxEvaluations = 2331;
        double intervalLowerBound = -1476.2544696353405;
        double intervalUpperBound = 4.800501435803201E-220;

        double root = solver.solve(maxEvaluations,
                                   (UnivariateFunction) cubeRoot,
                                   intervalLowerBound,
                                   intervalUpperBound);

        assertEquals(4.800501435803201E-220, root, 0.01);
    }
}
