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
public class RandomUtils_ESTest_test06 extends RandomUtils_ESTest_scaffolding {

    private static final double NEGATIVE_START = -1.0;
    private static final double VALID_END = 4913.0790244406835;

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        RandomUtils randomUtils = new RandomUtils();
        // randomDouble requires a non-negative start; a negative start must throw
        try {
            randomUtils.randomDouble(NEGATIVE_START, VALID_END);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
