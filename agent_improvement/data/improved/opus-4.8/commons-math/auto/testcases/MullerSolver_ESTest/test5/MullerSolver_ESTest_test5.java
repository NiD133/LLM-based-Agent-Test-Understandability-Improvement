package org.apache.commons.math4.legacy.analysis.solvers;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Ulp;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MullerSolver_ESTest_test5 extends MullerSolver_ESTest_scaffolding {

    /**
     * When the starting point is already (numerically) a root of the function,
     * the solver should converge and return that same point.
     *
     * The Ulp function returns the size of the unit in the last place of its
     * argument, a tiny positive value, so for the purposes of this solver the
     * supplied start point is effectively a fixed point.
     */
    @Test(timeout = 4000)
    public void solveReturnsStartingPointWhenItIsAlreadyARoot() throws Throwable {
        double relativeAccuracy = 3.1660099222737955E-7;
        double absoluteAccuracy = -4620.5448992389;
        MullerSolver solver = new MullerSolver(relativeAccuracy, absoluteAccuracy);

        UnivariateFunction ulpFunction = new Ulp();
        int maxEvaluations = 2048;
        double startPoint = 1.5902118682861328;

        double root = solver.solve(maxEvaluations, ulpFunction, startPoint);

        assertEquals(startPoint, root, 0.01);
    }
}
