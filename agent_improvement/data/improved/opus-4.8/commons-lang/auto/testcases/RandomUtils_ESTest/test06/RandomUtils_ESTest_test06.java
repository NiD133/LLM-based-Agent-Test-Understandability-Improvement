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
public class RandomUtils_ESTest_test06 extends RandomUtils_ESTest_scaffolding {

    /**
     * randomDouble(startInclusive, endExclusive) requires both range bounds to be
     * non-negative. A negative startInclusive must be rejected with an
     * IllegalArgumentException raised by Validate, even when startInclusive is less
     * than endExclusive.
     */
    @Test(timeout = 4000)
    public void randomDouble_withNegativeStart_throwsIllegalArgumentException() throws Throwable {
        RandomUtils randomUtils = new RandomUtils();

        double negativeStart = -1.0;
        double validEnd = 4913.0790244406835;

        try {
            randomUtils.randomDouble(negativeStart, validEnd);
            fail("Expected IllegalArgumentException because the start value is negative");
        } catch (IllegalArgumentException e) {
            // Message: "Both range values must be non-negative."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
