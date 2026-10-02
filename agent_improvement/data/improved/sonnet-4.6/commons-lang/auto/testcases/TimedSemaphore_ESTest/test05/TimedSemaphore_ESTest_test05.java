package org.apache.commons.lang3.concurrent;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TimedSemaphore_ESTest_test05 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Verifies that calling acquire() on a shut-down TimedSemaphore throws
     * IllegalStateException, even after the limit has been changed post-shutdown.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Create a semaphore with a 3049-microsecond period and no acquire limit
        TimedSemaphore semaphore = new TimedSemaphore(3049L, TimeUnit.MICROSECONDS, 0);

        // Simulate two timer-period completions to advance period/acquire counters
        semaphore.endOfPeriod();
        semaphore.endOfPeriod();

        // Exercise the static builder factory (return value unused)
        TimedSemaphore.builder();

        // Start the internal periodic timer task
        semaphore.startTimer();

        // Update the limit while the semaphore is active (0 = unlimited)
        semaphore.setLimit(0);

        // Exercise the static builder factory a second time (return value unused)
        TimedSemaphore.builder();

        // Complete another period and read the average calls-per-period metric
        semaphore.endOfPeriod();
        semaphore.getAverageCallsPerPeriod();

        // Shut down the semaphore — acquire() must not be called after this point
        semaphore.shutdown();

        // Changing the limit after shutdown should not itself throw
        semaphore.setLimit(-1021);

        // Acquiring a permit on a shut-down semaphore must throw IllegalStateException
        try {
            semaphore.acquire();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.lang3.concurrent.TimedSemaphore", e);
        }
    }
}
