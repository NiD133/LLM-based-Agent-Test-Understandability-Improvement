package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Sigmoid_ESTest_test2 extends Sigmoid_ESTest_scaffolding {

    /**
     * Sigmoid.Parametric#gradient requires exactly two parameters
     * (the lower asymptote and the higher asymptote). Passing a parameter
     * array of the wrong length must be rejected with a RuntimeException
     * whose message reports the size mismatch ("1 != 2").
     */
    @Test(timeout = 4000)
    public void gradientWithWrongNumberOfParametersThrows() throws Throwable {
        Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();

        // Only one parameter is supplied, but gradient expects two.
        double[] tooFewParameters = new double[1];
        double anyInputValue = -743.321909624;

        try {
            sigmoid.gradient(anyInputValue, tooFewParameters);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // The parameter array length (1) does not match the required length (2).
            verifyException("org.apache.commons.math4.legacy.analysis.function.Sigmoid$Parametric", e);
        }
    }
}
