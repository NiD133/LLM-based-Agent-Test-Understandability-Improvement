package org.apache.commons.math4.legacy.analysis.solvers;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Cbrt;
import org.apache.commons.math4.legacy.analysis.function.Minus;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.legacy.analysis.function.Tan;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SecantSolver_ESTest_test2 extends SecantSolver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        SecantSolver secantSolver0 = new SecantSolver(0.0);
        Tan tan0 = new Tan();
        double double0 = secantSolver0.solve(2392, (UnivariateFunction) tan0, (-1.5574077246549023), 0.0);
        assertEquals(0.0, double0, 0.01);
    }
}
