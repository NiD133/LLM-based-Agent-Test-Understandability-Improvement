package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Sigmoid_ESTest_test3 extends Sigmoid_ESTest_scaffolding {

    /**
     * Sigmoid.Parametric#gradient requires a non-null parameter array.
     * Passing null for the parameters must raise a NullPointerException.
     */
    @Test(timeout = 4000)
    public void gradientWithNullParametersThrowsNullPointerException() throws Throwable {
        Sigmoid.Parametric parametricSigmoid = new Sigmoid.Parametric();

        double inputValue = 1.0;
        double[] nullParameters = null;

        try {
            parametricSigmoid.gradient(inputValue, nullParameters);
            fail("Expected a NullPointerException because null parameters are not allowed");
        } catch (NullPointerException expected) {
            // gradient(...) rejects a null parameter array
            verifyException("org.apache.commons.math4.legacy.analysis.function.Sigmoid$Parametric", expected);
        }
    }
}
