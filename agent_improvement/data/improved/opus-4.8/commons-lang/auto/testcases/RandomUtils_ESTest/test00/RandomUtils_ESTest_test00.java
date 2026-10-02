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
public class RandomUtils_ESTest_test00 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link RandomUtils#randomLong(long, long)} rejects a range whose
     * start value is greater than its end value by throwing an
     * {@link IllegalArgumentException}, as enforced by {@code Validate}.
     */
    @Test(timeout = 4000)
    public void randomLong_whenStartGreaterThanEnd_throwsIllegalArgumentException() throws Throwable {
        RandomUtils randomUtils = RandomUtils.insecure();

        long startInclusive = 8693331374357696304L;
        long endExclusive = 3803L;

        try {
            randomUtils.randomLong(startInclusive, endExclusive);
            fail("Expected an IllegalArgumentException because the start value exceeds the end value.");
        } catch (IllegalArgumentException e) {
            // Validate reports: "Start value must be smaller or equal to end value."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
