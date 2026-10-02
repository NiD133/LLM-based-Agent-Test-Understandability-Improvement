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
public class TimedSemaphore_ESTest_test14 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * When the semaphore limit is set to NO_LIMIT (0), both tryAcquire() and acquire()
     * pass through immediately without blocking. getAvailablePermits() returns
     * limit - acquireCount, which becomes negative after two successful acquisitions.
     */
    @Test(timeout = 4000)
    public void test_availablePermitsIsNegativeAfterTwoAcquisitionsWithNoLimit() throws Throwable {
        // A semaphore with NO_LIMIT (0) never blocks callers regardless of how many acquire.
        TimedSemaphore semaphore = new TimedSemaphore(1091L, TimeUnit.HOURS, TimedSemaphore.NO_LIMIT);

        semaphore.tryAcquire(); // acquireCount becomes 1
        semaphore.acquire();    // acquireCount becomes 2

        // availablePermits = limit - acquireCount = 0 - 2 = -2
        assertEquals(-2, semaphore.getAvailablePermits());
    }
}
