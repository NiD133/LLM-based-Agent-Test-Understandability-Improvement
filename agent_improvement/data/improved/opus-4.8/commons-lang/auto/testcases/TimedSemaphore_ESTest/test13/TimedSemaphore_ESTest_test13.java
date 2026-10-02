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

    /**
     * Exercises the normal lifecycle of a {@link TimedSemaphore} (end-of-period,
     * tryAcquire, repeated shutdown) and then verifies that constructing a
     * semaphore with a non-positive time period is rejected with an
     * {@link IllegalArgumentException}.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // A semaphore with a 1228-hour period and a negative limit (treated as "no limit").
        TimedSemaphore semaphore = new TimedSemaphore(1228L, TimeUnit.HOURS, -2);

        // Drive the lifecycle: roll over the period, try to acquire a permit,
        // then shut down twice (the second shutdown must be a harmless no-op).
        semaphore.endOfPeriod();
        semaphore.tryAcquire();
        semaphore.shutdown();
        semaphore.shutdown();

        // The builder factory should be reachable.
        TimedSemaphore.builder();

        // A negative time period is invalid and must be rejected at construction.
        try {
            new TimedSemaphore((ScheduledExecutorService) null, -1229L, TimeUnit.NANOSECONDS, -745);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Validate rejects the period: "Time period must be greater than 0."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
