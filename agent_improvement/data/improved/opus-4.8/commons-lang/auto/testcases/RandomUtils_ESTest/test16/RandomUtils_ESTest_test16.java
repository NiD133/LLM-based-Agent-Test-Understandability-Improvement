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
     * Verifies that the static {@link RandomUtils#nextBoolean()} factory method
     * can be invoked and completes successfully.
     *
     * The returned value is random, so its exact boolean value cannot be
     * asserted; this test only confirms the call returns without throwing.
     */
    @Test(timeout = 4000)
    public void nextBooleanReturnsWithoutError() throws Throwable {
        // Generate a random boolean using the static convenience method.
        boolean randomResult = RandomUtils.nextBoolean();

        // The result is non-deterministic, so no value-based assertion is made.
    }
}
