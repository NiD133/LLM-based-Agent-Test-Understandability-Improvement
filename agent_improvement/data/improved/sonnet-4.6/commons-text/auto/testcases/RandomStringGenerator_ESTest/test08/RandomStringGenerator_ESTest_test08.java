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
public class RandomStringGenerator_ESTest_test08 extends RandomStringGenerator_ESTest_scaffolding {

    // A code point value that exceeds Character.MAX_CODE_POINT (0x10FFFF = 1114111)
    private static final int EXCEEDS_MAX_CODE_POINT = 2064888123;

    @Test(timeout = 4000)
    public void test08_withinRange_throwsWhenCodePointExceedsMaximumAllowed() throws Throwable {
        RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();
        try {
            builder.withinRange(EXCEEDS_MAX_CODE_POINT, EXCEEDS_MAX_CODE_POINT);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Value 2064888123 is larger than Character.MAX_CODE_POINT.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
