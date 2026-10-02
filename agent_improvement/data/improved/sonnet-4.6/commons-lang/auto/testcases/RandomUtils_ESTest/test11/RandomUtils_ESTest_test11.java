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
     * Verifies that the deprecated static {@link RandomUtils#nextLong()} can be invoked without
     * throwing an exception. No value assertion is made because the mocked JVM returns a
     * non-deterministic result across EvoSuite runs.
     */
    @Test(timeout = 4000)
    public void test11_nextLong_returnsWithoutException() throws Throwable {
        long randomLong = RandomUtils.nextLong();
        //  // Unstable assertion: assertEquals(2763674547277152786L, randomLong);
    }
}
