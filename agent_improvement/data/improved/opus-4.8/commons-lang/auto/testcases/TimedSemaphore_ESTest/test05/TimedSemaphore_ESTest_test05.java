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
public class TimedSemaphore_ESTest_test05 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Verifies that once a {@link TimedSemaphore} has been shut down, any later
     * call to {@link TimedSemaphore#acquire()} fails with an
     * {@link IllegalStateException}, regardless of how the limit is configured.
     */
    @Test(timeout = 4000)
    public void acquireAfterShutdownThrowsIllegalStateException() throws Throwable {
        // Create a semaphore with a 3049 microsecond period and no limit (limit == 0).
        final TimedSemaphore semaphore = new TimedSemaphore(3049L, TimeUnit.MICROSECONDS, 0);

        // Exercise the bookkeeping methods while the semaphore is still active.
        semaphore.endOfPeriod();
        semaphore.endOfPeriod();
        TimedSemaphore.builder();
        semaphore.startTimer();
        semaphore.setLimit(0);
        TimedSemaphore.builder();
        semaphore.endOfPeriod();
        semaphore.getAverageCallsPerPeriod();

        // Shut the semaphore down; further reconfiguration must not revive it.
        semaphore.shutdown();
        semaphore.setLimit(-1021);

        // acquire() on a shut-down semaphore must be rejected.
        try {
            semaphore.acquire();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Expected: "TimedSemaphore is shut down."
            verifyException("org.apache.commons.lang3.concurrent.TimedSemaphore", e);
        }
    }
}
