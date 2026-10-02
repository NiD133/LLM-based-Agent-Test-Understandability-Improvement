package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.function.IntUnaryOperator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test03 extends RandomStringGenerator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        RandomStringGenerator.Builder randomStringGenerator_Builder0 = RandomStringGenerator.builder();
        RandomStringGenerator randomStringGenerator0 = randomStringGenerator_Builder0.get();
        // Undeclared exception!
        try {
            randomStringGenerator0.generate((-1), 1114111);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Minimum length -1 is smaller than zero.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
