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
public class RandomStringGenerator_ESTest_test11 extends RandomStringGenerator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void withinRangeThrowsWhenMinimumCodePointExceedsMaximum() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        try {
            builder.withinRange(1114111, 0);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            verifyException("org.apache.commons.lang3.Validate", exception);
        }
    }
}
