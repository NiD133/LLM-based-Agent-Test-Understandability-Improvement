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
public class TimedSemaphore_ESTest_test03 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Exercises the normal life-cycle of a {@link TimedSemaphore} (create, run its
     * timer, acquire a permit, then shut it down twice) and then verifies that
     * building a semaphore with a non-positive time period is rejected.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Create a semaphore allowing 8 acquisitions per 1228-hour period.
        TimedSemaphore semaphore = new TimedSemaphore(1228L, TimeUnit.HOURS, 8);

        // Drive the semaphore through its main operations.
        semaphore.endOfPeriod();
        semaphore.startTimer();
        semaphore.tryAcquire();

        // shutdown() is idempotent: the second call must have no effect.
        semaphore.shutdown();
        semaphore.shutdown();

        // The builder factory should hand back a usable Builder instance.
        TimedSemaphore.builder();

        // A time period of zero or less is invalid and must be rejected.
        try {
            new TimedSemaphore((ScheduledExecutorService) null, -1229L, TimeUnit.NANOSECONDS, -745);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Validate reports: "Time period must be greater than 0."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
