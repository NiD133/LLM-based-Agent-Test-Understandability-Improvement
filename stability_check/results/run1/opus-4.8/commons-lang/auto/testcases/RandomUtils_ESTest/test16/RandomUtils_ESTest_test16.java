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
     * Verifies that {@link RandomUtils#nextBoolean()} can be invoked and simply
     * returns without throwing. The returned value is genuinely random, so its
     * exact value (true or false) is not asserted.
     */
    @Test(timeout = 4000)
    public void nextBooleanReturnsWithoutError() throws Throwable {
        // Invoke the static random-boolean generator; either outcome is acceptable.
        RandomUtils.nextBoolean();
    }
}
