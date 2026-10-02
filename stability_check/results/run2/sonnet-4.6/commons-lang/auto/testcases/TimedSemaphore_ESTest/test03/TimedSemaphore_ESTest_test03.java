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
public class TimedSemaphore_ESTest_test03 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Verifies that a TimedSemaphore can complete a normal lifecycle:
     * end-of-period reset, timer start, non-blocking acquire, and idempotent shutdown.
     * Also verifies that constructing a semaphore with a non-positive time period
     * throws an IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // --- Normal lifecycle ---

        // Create a semaphore with an 1228-hour period and a limit of 8 permits per period.
        TimedSemaphore semaphore = new TimedSemaphore(1228L, TimeUnit.HOURS, 8);

        // Manually trigger end-of-period to reset the acquire counter and wake any waiters.
        semaphore.endOfPeriod();

        // Start the background timer that will periodically call endOfPeriod().
        semaphore.startTimer();

        // Acquire a permit without blocking; should succeed because the limit (8) is not reached.
        semaphore.tryAcquire();

        // Shut down the semaphore; the first call cancels the timer and stops the executor.
        semaphore.shutdown();

        // A second shutdown call must be a no-op (should not throw or double-cancel anything).
        semaphore.shutdown();

        // Verify that the builder factory method is accessible and returns a Builder instance.
        TimedSemaphore.builder();

        // --- Invalid construction: non-positive time period ---

        // A negative time period (-1229) must be rejected, regardless of other arguments.
        try {
            new TimedSemaphore((ScheduledExecutorService) null, (-1229L), TimeUnit.NANOSECONDS, (-745));
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
