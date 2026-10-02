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

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Build a semaphore with a 2475-unit period and a limit of 328 permits
        TimedSemaphore.Builder builder = new TimedSemaphore.Builder();
        builder.setPeriod(2475L);
        builder.setLimit(328);
        TimedSemaphore semaphore = builder.get();

        // Shut down the semaphore and invoke endOfPeriod() on the shutdown instance
        semaphore.shutdown();
        semaphore.endOfPeriod();

        // Reconfigure the builder with a new time unit; available permits are still
        // readable on the shutdown semaphore
        builder.setTimeUnit(TimeUnit.MINUTES);
        semaphore.getAvailablePermits();

        // setLimit() returns 'this', so builderWithNegativeLimit is the same object as builder
        builder.setLimit(0);
        TimedSemaphore.Builder builderWithNegativeLimit = builder.setLimit(-1246);
        semaphore.getAvailablePermits();

        // Update the period and read the limit from the shutdown semaphore
        builder.setPeriod(328);
        semaphore.getLimit();

        // Build a second semaphore (period=328, limit=-1246, timeUnit=MINUTES)
        TimedSemaphore semaphoreWithNegativeLimit = builderWithNegativeLimit.get();
        semaphore.getAvailablePermits();

        // Calling shutdown() a second time on an already-shutdown semaphore is harmless
        semaphore.shutdown();

        // Multiple calls to get() on the same builder produce independent semaphore instances
        builderWithNegativeLimit.get();
        semaphore.getAvailablePermits();
        builderWithNegativeLimit.get();

        // Further reconfigure the builder (same object as builder): limit=328, period=0, service=null
        builderWithNegativeLimit.setLimit(328);
        builderWithNegativeLimit.setPeriod(0);
        builderWithNegativeLimit.setService((ScheduledExecutorService) null);

        // Read state from the shutdown semaphores
        semaphore.getAcquireCount();
        semaphoreWithNegativeLimit.getUnit();

        // Building with period=0 must throw IllegalArgumentException because period must be > 0
        try {
            builder.get();
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Time period must be greater than 0.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
