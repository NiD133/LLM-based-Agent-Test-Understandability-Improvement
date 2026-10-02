package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test15 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link RandomUtils#nextDouble()} can be invoked without throwing an exception.
     * The exact value is non-deterministic, so the value assertion is omitted (marked unstable).
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        double randomDouble = RandomUtils.nextDouble();
        //  // Unstable assertion: assertEquals(1.081940433783933E308, randomDouble, 0.01);
    }
}
