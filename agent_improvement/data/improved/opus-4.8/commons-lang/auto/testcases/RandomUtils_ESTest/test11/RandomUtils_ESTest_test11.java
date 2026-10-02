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
public class RandomUtils_ESTest_test11 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that the static {@link RandomUtils#nextLong()} helper can be called
     * without throwing. Its result is non-deterministic, so the exact value cannot
     * be asserted (the original generated test left an "unstable" equality check
     * commented out for that reason).
     */
    @Test(timeout = 4000)
    public void nextLongReturnsValueWithoutThrowing() throws Throwable {
        // nextLong() yields a random long; we only confirm the call succeeds.
        long randomValue = RandomUtils.nextLong();
    }
}
