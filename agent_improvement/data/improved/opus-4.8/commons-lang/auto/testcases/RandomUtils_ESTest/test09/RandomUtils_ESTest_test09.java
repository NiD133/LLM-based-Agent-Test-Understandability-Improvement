package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test09 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link RandomUtils#nextLong(long, long)} rejects a negative
     * lower bound: both range values must be non-negative, so a negative
     * {@code startInclusive} must trigger an IllegalArgumentException raised by Validate.
     */
    @Test(timeout = 4000)
    public void nextLongWithNegativeStartThrowsIllegalArgumentException() throws Throwable {
        long negativeStartInclusive = -176L;
        long endExclusive = 819016964741450255L;

        try {
            RandomUtils.nextLong(negativeStartInclusive, endExclusive);
            fail("Expected IllegalArgumentException because the start value is negative.");
        } catch (IllegalArgumentException e) {
            // Message: "Both range values must be non-negative."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
