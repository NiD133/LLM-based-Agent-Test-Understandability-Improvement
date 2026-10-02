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
public class TimedSemaphore_ESTest_test10 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Tests that acquire() succeeds without blocking after endOfPeriod() is called
     * when the semaphore limit is set below NO_LIMIT (i.e., unlimited mode).
     *
     * A negative limit disables enforcement, so acquire() never blocks.
     * Calling endOfPeriod() before acquire() resets the period counter,
     * which should not prevent a subsequent acquire() from succeeding.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // Verify the builder() factory method is accessible
        TimedSemaphore.builder();

        // Create a semaphore with a 4081-hour period and a negative limit.
        // A limit of -1727 is less than NO_LIMIT (0), so the semaphore is in
        // unlimited mode: acquire() never blocks regardless of call count.
        long periodInHours = 4081L;
        int unlimitedLimit = -1727;
        TimedSemaphore semaphore = new TimedSemaphore(periodInHours, TimeUnit.HOURS, unlimitedLimit);

        // Simulate the end of a time period: resets the acquire counter and
        // wakes up any waiting threads. Should be safe to call at any point.
        semaphore.endOfPeriod();

        // With an unlimited limit, acquire() must succeed (not block or throw).
        semaphore.acquire();
    }
}
