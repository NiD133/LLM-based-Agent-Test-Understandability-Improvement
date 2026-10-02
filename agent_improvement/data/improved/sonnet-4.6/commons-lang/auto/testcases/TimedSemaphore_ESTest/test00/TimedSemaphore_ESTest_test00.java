package org.apache.commons.lang3.concurrent;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.evosuite.runtime.mock.java.lang.MockThread;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TimedSemaphore_ESTest_test00 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Verifies that calling acquire() on a shut-down TimedSemaphore throws
     * IllegalStateException, while a still-active semaphore continues to work normally.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Create an active semaphore with a very long period and a high permit limit
        TimeUnit microseconds = TimeUnit.MICROSECONDS;
        TimedSemaphore activeSemaphore = new TimedSemaphore(Long.MAX_VALUE, microseconds, 1250);

        // First acquire starts the internal timer task
        activeSemaphore.acquire();
        activeSemaphore.startTimer();

        // Obtain a builder and exercise the non-blocking tryAcquire / getLimit paths
        TimedSemaphore.Builder builder = TimedSemaphore.builder();
        activeSemaphore.tryAcquire();
        activeSemaphore.getLimit();
        activeSemaphore.acquire();

        // Configure the builder (result is intentionally discarded here)
        builder.setPeriod(0L);
        TimedSemaphore.builder();

        // Create a second semaphore and immediately shut it down
        TimeUnit hours = TimeUnit.HOURS;
        TimedSemaphore shutdownSemaphore = new TimedSemaphore(1239L, hours, 1);
        shutdownSemaphore.shutdown();

        // The active semaphore still accepts new acquires after an unrelated shutdown
        activeSemaphore.acquire();

        // Average is 0.0 because the shut-down semaphore never completed a period
        shutdownSemaphore.getAverageCallsPerPeriod();

        // Acquiring from a shut-down semaphore must throw IllegalStateException
        try {
            shutdownSemaphore.acquire();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // TimedSemaphore is shut down.
            //
            verifyException("org.apache.commons.lang3.concurrent.TimedSemaphore", e);
        }
    }
}
