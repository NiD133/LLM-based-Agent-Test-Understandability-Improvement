package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test02 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link RandomUtils#nextInt(int, int)} rejects a range whose
     * start is greater than its end. Here the inclusive start (1977) exceeds the
     * exclusive end (1), so the method must reject the invalid range by throwing
     * an IllegalArgumentException raised by the internal Validate check.
     */
    @Test(timeout = 4000)
    public void nextIntWithStartGreaterThanEndThrowsIllegalArgumentException() throws Throwable {
        int startInclusive = 1977;
        int endExclusive = 1;

        try {
            RandomUtils.nextInt(startInclusive, endExclusive);
            fail("Expected an IllegalArgumentException because start must be <= end.");
        } catch (IllegalArgumentException e) {
            // The range is validated by org.apache.commons.lang3.Validate, which
            // fails with: "Start value must be smaller or equal to end value."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
