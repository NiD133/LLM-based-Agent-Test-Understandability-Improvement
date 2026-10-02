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
public class TimedSemaphore_ESTest_test03 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Verifies the normal semaphore lifecycle (endOfPeriod → startTimer → tryAcquire → shutdown)
     * and that shutdown() is idempotent. Also verifies that the builder() factory method is
     * accessible, and that constructing a semaphore with a non-positive period throws
     * IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Create a semaphore with a 1228-hour period and a limit of 8 permits
        TimedSemaphore semaphore = new TimedSemaphore(1228L, TimeUnit.HOURS, 8);

        // Simulate an end-of-period event, then start the background timer
        semaphore.endOfPeriod();
        semaphore.startTimer();

        // tryAcquire() should succeed because the limit has not been reached
        semaphore.tryAcquire();

        // shutdown() should release resources; calling it again must be a no-op
        semaphore.shutdown();
        semaphore.shutdown();

        // Verify the static builder() factory method is accessible
        TimedSemaphore.builder();

        // A negative time period must be rejected with an IllegalArgumentException
        try {
            new TimedSemaphore((ScheduledExecutorService) null, (-1229L), TimeUnit.NANOSECONDS, (-745));
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
