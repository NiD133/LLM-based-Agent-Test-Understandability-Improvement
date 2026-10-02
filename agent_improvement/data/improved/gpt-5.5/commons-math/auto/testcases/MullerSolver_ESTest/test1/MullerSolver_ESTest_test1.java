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
        MullerSolver solver = new MullerSolver();
        Sin sineFunction = new Sin();
        UnivariateFunction function = sineFunction;
        int maximumEvaluations = 2331;
        double minimum = 3824.7097985564;
        double maximum = 3.8581732071331E174;

        try {
            solver.solve(maximumEvaluations, function, minimum, maximum);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException exception) {
            //
            // illegal state: maximal count (2,331) exceeded: evaluations
            //
            verifyException("org.apache.commons.math4.legacy.analysis.solvers.BaseAbstractUnivariateSolver", exception);
        }
    }
}
