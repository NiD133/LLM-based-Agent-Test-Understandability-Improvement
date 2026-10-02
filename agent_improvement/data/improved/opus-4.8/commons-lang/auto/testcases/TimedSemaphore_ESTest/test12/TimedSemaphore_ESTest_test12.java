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
public class TimedSemaphore_ESTest_test12 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Verifies that once a {@link TimedSemaphore} has been shut down, calling
     * {@link TimedSemaphore#acquire()} fails with an {@link IllegalStateException}.
     *
     * The test also confirms that the semaphore tolerates being shut down
     * repeatedly (shutdown() is idempotent) before the final acquire() attempt.
     */
    @Test(timeout = 4000)
    public void acquireAfterShutdownThrowsIllegalStateException() throws Throwable {
        // A limit of 0 disables the semaphore (NO_LIMIT), so acquisitions never block.
        final long period = 1231L;
        final int noLimit = 0;
        TimedSemaphore semaphore = new TimedSemaphore(period, TimeUnit.HOURS, noLimit);

        // Exercise the semaphore while it is still active.
        semaphore.endOfPeriod();
        semaphore.tryAcquire();

        // Shutting down multiple times must be harmless; only the first call has an effect.
        semaphore.shutdown();
        semaphore.shutdown();
        TimedSemaphore.builder();
        semaphore.shutdown();

        // After shutdown the semaphore can no longer be used: acquire() must fail.
        try {
            semaphore.acquire();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Expected: "TimedSemaphore is shut down."
            verifyException("org.apache.commons.lang3.concurrent.TimedSemaphore", e);
        }
    }
}
