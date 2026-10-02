package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test20 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link RandomUtils#nextBytes(int)} returns a byte array
     * whose length equals the requested count.
     */
    @Test(timeout = 4000)
    public void nextBytesReturnsArrayOfRequestedLength() throws Throwable {
        final int requestedLength = 1335;

        byte[] randomBytes = RandomUtils.nextBytes(requestedLength);

        assertEquals(requestedLength, randomBytes.length);
    }
}
