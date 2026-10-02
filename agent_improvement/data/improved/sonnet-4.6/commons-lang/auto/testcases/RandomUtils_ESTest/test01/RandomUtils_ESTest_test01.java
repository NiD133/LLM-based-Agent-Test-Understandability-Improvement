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
public class RandomUtils_ESTest_test01 extends RandomUtils_ESTest_scaffolding {

    private static final int NEGATIVE_START = -967;
    private static final int ZERO_END = 0;

    @Test(timeout = 4000)
    public void test01_nextInt_throwsWhenStartIsNegative() throws Throwable {
        try {
            RandomUtils.nextInt(NEGATIVE_START, ZERO_END);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
