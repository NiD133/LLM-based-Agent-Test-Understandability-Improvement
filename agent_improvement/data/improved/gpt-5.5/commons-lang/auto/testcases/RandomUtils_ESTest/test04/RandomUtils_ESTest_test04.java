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
public class RandomUtils_ESTest_test04 extends RandomUtils_ESTest_scaffolding {

    private static final float START_INCLUSIVE = 0.0F;
    private static final float END_EXCLUSIVE_BEFORE_START = -614.1698F;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        try {
            RandomUtils.nextFloat(START_INCLUSIVE, END_EXCLUSIVE_BEFORE_START);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
