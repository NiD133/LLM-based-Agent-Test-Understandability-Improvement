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
public class RandomUtils_ESTest_test15 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that the static {@link RandomUtils#nextDouble()} convenience method
     * can be invoked and returns a value without throwing an exception.
     *
     * The exact returned value is non-deterministic (it is a random double in the
     * range [0, Double.MAX_VALUE)), so no assertion is made on the concrete result.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        double randomDouble = RandomUtils.nextDouble();
        // Result is random, so its specific value is not asserted.
    }
}
