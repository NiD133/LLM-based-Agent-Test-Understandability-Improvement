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
public class RandomStringGenerator_ESTest_test09 extends RandomStringGenerator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        try {
            builder.withinRange((-3302), (-3302));
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Negative minimum code points are rejected by the builder validation.
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
