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
public class TimedSemaphore_ESTest_test06 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Create a semaphore with a 3049-microsecond period and no acquire limit (limit=0 means NO_LIMIT)
        TimedSemaphore semaphore = new TimedSemaphore(3049L, TimeUnit.MICROSECONDS, 0);

        // Simulate two period endings to advance the period counter before the timer starts
        semaphore.endOfPeriod();
        semaphore.endOfPeriod();

        // Obtain a builder instance for later use
        TimedSemaphore.Builder builder = TimedSemaphore.builder();

        // Start the internal periodic timer task on the semaphore
        semaphore.startTimer();

        // Disable the limit explicitly (0 == NO_LIMIT: acquire() never blocks)
        semaphore.setLimit(0);

        // Exercise the static factory method; the returned builder is intentionally discarded
        TimedSemaphore.builder();

        // Simulate a third period ending to update the rolling statistics
        semaphore.endOfPeriod();

        // Retrieve the average acquire calls per period (covers all three completed periods)
        semaphore.getAverageCallsPerPeriod();

        // Shut down the semaphore, cancelling the scheduled timer task
        semaphore.shutdown();

        // Verify that setLimit still accepts negative values after shutdown (no exception expected)
        semaphore.setLimit(-1033);

        // Configure the previously obtained builder with a 36-unit period and build a new semaphore
        TimedSemaphore.Builder configuredBuilder = builder.setPeriod(36L);
        configuredBuilder.get();
    }
}
