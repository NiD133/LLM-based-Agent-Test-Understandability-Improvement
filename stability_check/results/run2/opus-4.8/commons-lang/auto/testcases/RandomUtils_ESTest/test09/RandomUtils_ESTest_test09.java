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
public class RandomUtils_ESTest_test09 extends RandomUtils_ESTest_scaffolding {

    /**
     * A negative lower bound is invalid: RandomUtils.nextLong requires both range
     * values to be non-negative, so it must reject a negative startInclusive by
     * throwing an IllegalArgumentException from Validate.
     */
    @Test(timeout = 4000)
    public void nextLongWithNegativeStartThrowsIllegalArgumentException() throws Throwable {
        long negativeStartInclusive = -176L;
        long endExclusive = 819016964741450255L;

        try {
            RandomUtils.nextLong(negativeStartInclusive, endExclusive);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "Both range values must be non-negative."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
