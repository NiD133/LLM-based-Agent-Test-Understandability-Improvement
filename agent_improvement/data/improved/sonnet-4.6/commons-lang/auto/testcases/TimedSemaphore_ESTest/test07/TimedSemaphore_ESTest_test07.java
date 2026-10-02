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
public class TimedSemaphore_ESTest_test07 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // --- Part 1: Semaphore with a negative limit survives shutdown and remains queryable ---

        // Use a large thread pool and MINUTES period so the background timer never fires
        // during the (mocked) test, avoiding race conditions on acquireCount.
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4507);
        TimeUnit periodUnit = TimeUnit.MINUTES;
        int negativeLimitValue = -2399;
        TimedSemaphore semaphore = new TimedSemaphore(executor, 4507, periodUnit, negativeLimitValue);

        // Manually trigger end-of-period (resets acquireCount and wakes waiting threads)
        semaphore.endOfPeriod();

        // First shutdown cancels the periodic task and marks the semaphore as shut down
        semaphore.shutdown();

        // Querying acquireCount after shutdown must not throw
        semaphore.getAcquireCount();

        // A second shutdown call on an already-shut-down semaphore must be a no-op
        semaphore.shutdown();

        // Querying the limit after shutdown must not throw
        semaphore.getLimit();

        // Verify that Builder can be instantiated (no configuration applied here)
        TimedSemaphore.Builder builder = new TimedSemaphore.Builder();

        // --- Part 2: Constructing a TimedSemaphore with a non-positive period is illegal ---

        TimedSemaphore semaphoreWithInvalidPeriod = null;
        try {
            // A period of -1 violates the "must be greater than 0" contract
            semaphoreWithInvalidPeriod = new TimedSemaphore((-1), periodUnit, 1);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Time period must be greater than 0.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
