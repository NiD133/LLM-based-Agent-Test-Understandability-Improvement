package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.security.SecureRandom;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test00 extends RandomUtils_ESTest_scaffolding {

    // start is far larger than end, so the range is inverted and must be rejected
    private static final long START_GREATER_THAN_END = 8693331374357696304L;
    private static final long END_LESS_THAN_START    = 3803L;

    @Test(timeout = 4000)
    public void test00_randomLong_startGreaterThanEnd_throwsIllegalArgumentException() throws Throwable {
        RandomUtils randomUtils = RandomUtils.insecure();
        try {
            randomUtils.randomLong(START_GREATER_THAN_END, END_LESS_THAN_START);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
