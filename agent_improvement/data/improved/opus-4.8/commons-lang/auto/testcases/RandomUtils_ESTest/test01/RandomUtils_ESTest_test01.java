package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test01 extends RandomUtils_ESTest_scaffolding {

    /**
     * RandomUtils.nextInt(startInclusive, endExclusive) requires both bounds to be
     * non-negative. A negative startInclusive must therefore be rejected with an
     * IllegalArgumentException, raised by the Validate helper class.
     */
    @Test(timeout = 4000)
    public void nextInt_withNegativeStart_throwsIllegalArgumentException() throws Throwable {
        final int negativeStart = -967;
        final int endExclusive = 0;

        try {
            RandomUtils.nextInt(negativeStart, endExclusive);
            fail("Expected IllegalArgumentException because the start value is negative");
        } catch (IllegalArgumentException e) {
            // Validate rejects the call with: "Both range values must be non-negative."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
