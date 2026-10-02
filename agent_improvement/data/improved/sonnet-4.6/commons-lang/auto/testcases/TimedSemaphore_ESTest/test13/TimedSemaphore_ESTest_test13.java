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
public class TimedSemaphore_ESTest_test13 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // Create a semaphore with a 1228-hour period and a negative limit (treated as no limit)
        TimeUnit hoursTimeUnit = TimeUnit.HOURS;
        TimedSemaphore semaphoreWithNegativeLimit = new TimedSemaphore(1228L, hoursTimeUnit, (-2));

        // Simulate the end of a period and verify tryAcquire succeeds (negative limit = no limit)
        semaphoreWithNegativeLimit.endOfPeriod();
        semaphoreWithNegativeLimit.tryAcquire();

        // Shutdown should be idempotent: calling it twice must not throw
        semaphoreWithNegativeLimit.shutdown();
        semaphoreWithNegativeLimit.shutdown();

        // Verify the builder factory method is accessible
        TimedSemaphore.builder();

        // A negative time period must be rejected with IllegalArgumentException
        TimeUnit nanosecondsTimeUnit = TimeUnit.NANOSECONDS;
        TimedSemaphore semaphoreWithNegativePeriod = null;
        try {
            semaphoreWithNegativePeriod = new TimedSemaphore((ScheduledExecutorService) null, (-1229L), nanosecondsTimeUnit, (-745));
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Time period must be greater than 0.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
