package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test04 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link RandomUtils#nextFloat(float, float)} rejects a range
     * whose start is greater than its end. Here the start (0) exceeds the end
     * (-614.1698), so the call must fail with an {@link IllegalArgumentException}
     * raised by {@code Validate} with the message
     * "Start value must be smaller or equal to end value.".
     */
    @Test(timeout = 4000)
    public void nextFloatRejectsStartGreaterThanEnd() throws Throwable {
        float startInclusive = 0.0F;
        float endExclusive = -614.1698F;

        try {
            RandomUtils.nextFloat(startInclusive, endExclusive);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Validate rejects the inverted range (start > end).
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
