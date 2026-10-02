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
     * When the limit is non-positive the semaphore is effectively switched off,
     * so {@link TimedSemaphore#acquire()} never blocks and returns immediately.
     * Here the limit is negative (-1727), which behaves like NO_LIMIT.
     */
    @Test(timeout = 4000)
    public void acquireDoesNotBlockWhenLimitIsNegative() throws Throwable {
        // Obtain a Builder (its result is not needed for this scenario).
        TimedSemaphore.builder();

        final long period = 4081L;
        final int negativeLimit = -1727;
        TimedSemaphore semaphore =
                new TimedSemaphore(period, TimeUnit.HOURS, negativeLimit);

        // Simulate the end of a time frame, which resets the acquire counter.
        semaphore.endOfPeriod();

        // With a non-positive limit, acquiring a permit succeeds without blocking.
        semaphore.acquire();
    }
}
