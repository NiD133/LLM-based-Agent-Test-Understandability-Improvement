package org.apache.commons.lang3.concurrent;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TimedSemaphore_ESTest_test01 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Exercises {@link TimedSemaphore.Builder} together with several inspector
     * methods on the produced {@link TimedSemaphore} instances, and finally
     * verifies that building with a non-positive period is rejected.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Configure a builder with a valid period and a limit, then build a semaphore.
        TimedSemaphore.Builder builder = new TimedSemaphore.Builder();
        builder.setPeriod(2475L);
        builder.setLimit(328);
        TimedSemaphore semaphore = builder.get();

        // Shutting down and ending the period are valid no-op-style state changes here.
        semaphore.shutdown();
        semaphore.endOfPeriod();

        // Mutating the builder after the fact does not affect the already-built semaphore.
        builder.setTimeUnit(TimeUnit.MINUTES);
        semaphore.getAvailablePermits();

        builder.setLimit(0);
        // setLimit returns the same builder instance (fluent API).
        TimedSemaphore.Builder sameBuilder = builder.setLimit(-1246);
        semaphore.getAvailablePermits();

        builder.setPeriod(328);
        semaphore.getLimit();

        // Build a second semaphore from the (still valid) builder configuration.
        TimedSemaphore secondSemaphore = sameBuilder.get();
        semaphore.getAvailablePermits();
        semaphore.shutdown();
        sameBuilder.get();
        semaphore.getAvailablePermits();
        sameBuilder.get();

        // Further builder mutations, including clearing the executor service.
        sameBuilder.setLimit(328);
        sameBuilder.setPeriod(0);
        sameBuilder.setService((ScheduledExecutorService) null);

        semaphore.getAcquireCount();
        secondSemaphore.getUnit();

        // The builder now has a period of 0, which is rejected by validation.
        try {
            builder.get();
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // "Time period must be greater than 0."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
