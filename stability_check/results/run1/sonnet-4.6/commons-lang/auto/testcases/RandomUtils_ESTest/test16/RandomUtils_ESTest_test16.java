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
public class RandomUtils_ESTest_test16 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link RandomUtils#nextBoolean()} executes without throwing an exception
     * and returns a valid boolean value. The specific value (true or false) is non-deterministic
     * and cannot be asserted reliably, so this test only confirms the method completes normally.
     */
    @Test(timeout = 4000)
    public void test_nextBoolean_completesWithoutException() throws Throwable {
        boolean result = RandomUtils.nextBoolean();
        // Result is either true or false; both are valid — no deterministic assertion possible
    }
}
