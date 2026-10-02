package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test16 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link RandomUtils#nextBoolean()} completes without throwing an exception.
     * The returned value is intentionally not asserted because it is non-deterministic.
     */
    @Test(timeout = 4000)
    public void test_nextBoolean_returnsWithoutException() throws Throwable {
        boolean result = RandomUtils.nextBoolean();
        // Result is a valid boolean — no assertion because the value is non-deterministic
    }
}
