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
public class Sigmoid_ESTest_test3 extends Sigmoid_ESTest_scaffolding {

    // Passing null as the parameters array to Sigmoid.Parametric.gradient() should
    // immediately throw NullPointerException — the implementation validates its input.
    @Test(timeout = 4000)
    public void test_gradient_throwsNullPointerException_whenParametersArrayIsNull() throws Throwable {
        Sigmoid.Parametric parametric = new Sigmoid.Parametric();
        try {
            parametric.gradient(1.0, (double[]) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.math4.legacy.analysis.function.Sigmoid$Parametric", e);
        }
    }
}
