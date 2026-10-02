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
public class SecantSolver_ESTest_test0 extends SecantSolver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        SecantSolver secantSolver0 = new SecantSolver();
        Sin sin0 = new Sin();
        secantSolver0.setup(819, sin0, 1.0E-6, 9.774993452612762, 1818.2683956272858);
        double double0 = secantSolver0.doSolve();
        assertEquals(4.843116750194934E-19, double0, 0.01);
    }
}
