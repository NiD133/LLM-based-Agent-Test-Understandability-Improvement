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
public class TimedSemaphore_ESTest_test00 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Exercises a never-blocking semaphore (limit far above any usage) and then
     * verifies that acquiring a permit from a semaphore that has already been
     * shut down raises an IllegalStateException.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // A semaphore with a huge limit (Long.MAX_VALUE as period, limit 1250):
        // acquire() never blocks because the limit is effectively unreachable.
        TimedSemaphore activeSemaphore =
                new TimedSemaphore(Long.MAX_VALUE, TimeUnit.MICROSECONDS, 1250);

        activeSemaphore.acquire();
        activeSemaphore.startTimer();

        // Builder API is reachable but does not affect the semaphore above.
        TimedSemaphore.Builder builder = TimedSemaphore.builder();

        activeSemaphore.tryAcquire();
        activeSemaphore.getLimit();
        activeSemaphore.acquire();

        builder.setPeriod(0L);
        TimedSemaphore.builder();

        // A second semaphore that we shut down before using it.
        TimedSemaphore shutDownSemaphore = new TimedSemaphore(1239L, TimeUnit.HOURS, 1);
        shutDownSemaphore.shutdown();

        activeSemaphore.acquire();
        shutDownSemaphore.getAverageCallsPerPeriod();

        // Acquiring on a shut-down semaphore is illegal.
        try {
            shutDownSemaphore.acquire();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // "TimedSemaphore is shut down."
            verifyException("org.apache.commons.lang3.concurrent.TimedSemaphore", e);
        }
    }
}
