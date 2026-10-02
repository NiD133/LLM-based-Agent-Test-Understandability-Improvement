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
public class RandomUtils_ESTest_test07 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link RandomUtils#randomDouble(double, double)} rejects a range
     * whose start value is greater than its end value. Here the start (1515.74...) is
     * larger than the end (390.80...), so the call must fail validation in
     * {@code org.apache.commons.lang3.Validate} with an IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void randomDouble_startGreaterThanEnd_throwsIllegalArgumentException() throws Throwable {
        RandomUtils secureRandomUtils = RandomUtils.secure();

        double startInclusive = 1515.7430316178;
        double endExclusive = 390.8004;

        try {
            secureRandomUtils.randomDouble(startInclusive, endExclusive);
            fail("Expected an IllegalArgumentException because start value exceeds end value.");
        } catch (IllegalArgumentException e) {
            // Validate rejects the range with: "Start value must be smaller or equal to end value."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
