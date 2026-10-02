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
public class RandomUtils_ESTest_test07 extends RandomUtils_ESTest_scaffolding {

    private static final double START_GREATER_THAN_END = 1515.7430316178;
    private static final double END_LESS_THAN_START = 390.8004;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        RandomUtils secureRandomUtils = RandomUtils.secure();

        try {
            secureRandomUtils.randomDouble(START_GREATER_THAN_END, END_LESS_THAN_START);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
