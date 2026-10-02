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
public class Sigmoid_ESTest_test2 extends Sigmoid_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        double[] singleParameter = new double[1];
        Sigmoid.Parametric parametricSigmoid = new Sigmoid.Parametric();

        try {
            parametricSigmoid.gradient((-743.321909624), singleParameter);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // The parametric sigmoid gradient requires two parameters.
            //
            verifyException("org.apache.commons.math4.legacy.analysis.function.Sigmoid$Parametric", e);
        }
    }
}
