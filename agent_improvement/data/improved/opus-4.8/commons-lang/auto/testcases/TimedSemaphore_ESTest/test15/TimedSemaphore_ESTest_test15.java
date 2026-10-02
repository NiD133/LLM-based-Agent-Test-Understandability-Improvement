package org.apache.commons.lang3.concurrent;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TimedSemaphore_ESTest_test15 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Constructing a TimedSemaphore with a non-positive time period must be rejected:
     * the constructor validates that the period is greater than 0 and throws an
     * IllegalArgumentException. Here a negative period (-2502) is supplied, so the
     * limit value (-4075) is never reached during validation.
     */
    @Test(timeout = 4000)
    public void constructorRejectsNonPositivePeriod() throws Throwable {
        final long negativePeriod = -2502L;
        final int limit = -4075;

        try {
            new TimedSemaphore(negativePeriod, TimeUnit.MILLISECONDS, limit);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Validate reports: "Time period must be greater than 0."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
