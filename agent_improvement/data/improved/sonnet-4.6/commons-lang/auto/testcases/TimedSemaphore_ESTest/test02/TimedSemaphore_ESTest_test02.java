package org.apache.commons.lang3.concurrent;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TimedSemaphore_ESTest_test02 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Verify that the builder() factory method returns a valid, non-null Builder instance
        TimedSemaphore.Builder builder = TimedSemaphore.builder();
        assertNotNull(builder);

        // Create a semaphore with a 4081-hour period and a negative limit.
        // A limit <= NO_LIMIT (0) disables the cap, so all acquire attempts will succeed.
        TimeUnit hours = TimeUnit.HOURS;
        TimedSemaphore unlimitedSemaphore = new TimedSemaphore(4081L, hours, (-1727));

        // tryAcquire() should succeed immediately because the negative limit disables the cap
        unlimitedSemaphore.tryAcquire();

        // Retrieve the raw limit value (still the negative value -1727 as originally set)
        unlimitedSemaphore.getLimit();

        // Configure the builder with a period of 0; this would be rejected when build() is called,
        // but setting it on the builder itself is allowed
        builder.setPeriod(0L);

        // getAvailablePermits() = getLimit() - getAcquireCount(); with a negative limit and one
        // successful acquire so far, the result is also negative
        unlimitedSemaphore.getAvailablePermits();

        // A second tryAcquire() also succeeds because the negative limit still disables the cap
        unlimitedSemaphore.tryAcquire();

        // Record the time unit in the builder for a future build
        builder.setTimeUnit(hours);

        // Create a second semaphore: pass null for the executor service so the class creates its own
        // internally, use a 1-millisecond period, and set limit=0 (NO_LIMIT) to allow unlimited acquires
        TimeUnit milliseconds = TimeUnit.MILLISECONDS;
        TimedSemaphore millisecondSemaphore = new TimedSemaphore((ScheduledExecutorService) null, 1L, milliseconds, 0);

        // Confirm that the time unit stored in the millisecond semaphore is MILLISECONDS
        millisecondSemaphore.getUnit();

        // Confirm that the first semaphore has not been shut down
        unlimitedSemaphore.isShutdown();

        // Confirm that the period stored in the millisecond semaphore is 1
        millisecondSemaphore.getPeriod();
    }
}
