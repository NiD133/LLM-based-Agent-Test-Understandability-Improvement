package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test08 extends RandomUtils_ESTest_scaffolding {

    /**
     * Requesting a byte array with a negative length must be rejected:
     * {@link RandomUtils#nextBytes(int)} validates the count and throws an
     * {@link IllegalArgumentException} ("Count cannot be negative.") via
     * {@code org.apache.commons.lang3.Validate}.
     */
    @Test(timeout = 4000)
    public void nextBytesWithNegativeCountThrowsIllegalArgumentException() throws Throwable {
        int negativeCount = -1862;

        try {
            RandomUtils.nextBytes(negativeCount);
            fail("Expected an IllegalArgumentException because the count is negative.");
        } catch (IllegalArgumentException e) {
            // The validation is performed by the Validate helper class.
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
