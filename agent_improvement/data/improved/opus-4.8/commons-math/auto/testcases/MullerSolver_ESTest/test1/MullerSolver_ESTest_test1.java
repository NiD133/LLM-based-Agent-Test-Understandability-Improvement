package org.apache.commons.math4.legacy.analysis.solvers;

import static org.evosuite.runtime.EvoAssertions.verifyException;
import static org.junit.Assert.fail;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MullerSolver_ESTest_test1 extends MullerSolver_ESTest_scaffolding {

    /**
     * Verifies that the solver aborts once it runs out of allowed function
     * evaluations. The evaluation budget (maxEval) is set to 2331, which is far
     * too small for the solver to converge on sin(x) over the very wide search
     * interval below, so the solver is expected to exhaust its budget and throw.
     */
    @Test(timeout = 4000)
    public void solveThrowsWhenEvaluationBudgetIsExceeded() throws Throwable {
        MullerSolver solver = new MullerSolver();
        UnivariateFunction sine = new Sin();

        int maxEvaluations = 2331;
        double intervalMin = 3824.7097985564;
        double intervalMax = 3.8581732071331E174;

        try {
            solver.solve(maxEvaluations, sine, intervalMin, intervalMax);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // The solver should report that it exceeded its evaluation budget, e.g.:
            // "illegal state: maximal count (2,331) exceeded: evaluations"
            verifyException("org.apache.commons.math4.legacy.analysis.solvers.BaseAbstractUnivariateSolver", e);
        }
    }
}
