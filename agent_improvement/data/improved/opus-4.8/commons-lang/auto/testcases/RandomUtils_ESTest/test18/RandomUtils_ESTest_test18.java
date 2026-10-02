package org.apache.commons.lang3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test18 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that the deprecated static {@link RandomUtils#nextInt()} can be
     * invoked without throwing. The concrete value it returns is random, so no
     * assertion is made on it (the original generator-produced equality check
     * was unstable across runs and therefore intentionally omitted).
     */
    @Test(timeout = 4000)
    public void test18() throws Throwable {
        RandomUtils.nextInt();
    }
}
