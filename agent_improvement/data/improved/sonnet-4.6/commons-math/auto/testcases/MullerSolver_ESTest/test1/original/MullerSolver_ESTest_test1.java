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
public class MullerSolver_ESTest_test1 extends MullerSolver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        MullerSolver mullerSolver0 = new MullerSolver();
        Sin sin0 = new Sin();
        try {
            mullerSolver0.solve(2331, (UnivariateFunction) sin0, 3824.7097985564, 3.8581732071331E174);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // illegal state: maximal count (2,331) exceeded: evaluations
            //
            verifyException("org.apache.commons.math4.legacy.analysis.solvers.BaseAbstractUnivariateSolver", e);
        }
    }
}
