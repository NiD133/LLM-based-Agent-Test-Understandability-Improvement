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
public class RandomUtils_ESTest_test18 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that the no-arg {@link RandomUtils#nextInt()} returns a value
     * within the documented range [0, Integer.MAX_VALUE).
     */
    @Test(timeout = 4000)
    public void test_nextInt_noArg_returnsValueInValidRange() throws Throwable {
        int result = RandomUtils.nextInt();

        assertTrue("nextInt() must return a non-negative value", result >= 0);
        assertTrue("nextInt() must return a value less than Integer.MAX_VALUE", result < Integer.MAX_VALUE);
    }
}
