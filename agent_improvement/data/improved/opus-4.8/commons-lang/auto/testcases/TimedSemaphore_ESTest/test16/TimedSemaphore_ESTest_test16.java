package org.apache.commons.lang3.concurrent;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TimedSemaphore_ESTest_test16 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Building a TimedSemaphore with a non-positive period must fail: the
     * constructor validates that the time period is greater than 0, so a
     * negative period triggers an IllegalArgumentException from Validate.
     */
    @Test(timeout = 4000)
    public void buildingWithNegativePeriodThrowsIllegalArgumentException() throws Throwable {
        final long negativePeriod = -2638L;
        TimedSemaphore.Builder builder = TimedSemaphore.builder().setPeriod(negativePeriod);

        try {
            builder.get();
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Time period must be greater than 0.
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
