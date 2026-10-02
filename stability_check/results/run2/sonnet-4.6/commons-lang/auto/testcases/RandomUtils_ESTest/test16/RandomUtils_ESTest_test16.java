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
     * Verifies that the deprecated static {@code RandomUtils.nextBoolean()} method
     * executes without throwing an exception and returns a valid boolean value.
     * The assertion on the returned value is omitted because the result is
     * non-deterministic (randomly true or false).
     */
    @Test(timeout = 4000)
    public void test_nextBoolean_returnsWithoutException() throws Throwable {
        boolean result = RandomUtils.nextBoolean();
        // Result is intentionally not asserted: the method returns a random boolean,
        // so any specific value check would be non-deterministic.
    }
}
