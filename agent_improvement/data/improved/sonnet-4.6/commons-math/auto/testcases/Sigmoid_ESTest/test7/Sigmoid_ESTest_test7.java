package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Sigmoid_ESTest_test7 extends Sigmoid_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test7_parametricSigmoidWithZeroAsymptotesAtOriginReturnsZero() throws Throwable {
        Sigmoid.Parametric parametricSigmoid = new Sigmoid.Parametric();

        // Parameters [lowerAsymptote=0.0, upperAsymptote=0.0]: both bounds are zero,
        // so the sigmoid output collapses to 0.0 for any input.
        double[] parameters = new double[2]; // {0.0, 0.0}

        double result = parametricSigmoid.value(0.0, parameters);

        assertEquals(0.0, result, 0.01);
    }
}
