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
public class RandomUtils_ESTest_test02 extends RandomUtils_ESTest_scaffolding {

    private static final int START_GREATER_THAN_END = 1977;
    private static final int END_BEFORE_START = 1;

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        try {
            RandomUtils.nextInt(START_GREATER_THAN_END, END_BEFORE_START);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
