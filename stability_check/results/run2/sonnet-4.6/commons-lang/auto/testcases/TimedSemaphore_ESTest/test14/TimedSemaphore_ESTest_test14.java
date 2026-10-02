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
     * When a TimedSemaphore is created with limit == NO_LIMIT (0), the "no limit" mode
     * causes acquirePermit() to always succeed (condition: limit <= NO_LIMIT).
     * Both tryAcquire() and acquire() increment the internal counter without blocking.
     * getAvailablePermits() = limit - acquireCount = 0 - 2 = -2.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // Create a semaphore with NO_LIMIT (0), so every acquire always succeeds
        TimedSemaphore semaphore = new TimedSemaphore(1091L, TimeUnit.HOURS, TimedSemaphore.NO_LIMIT);

        // Both calls succeed because limit <= NO_LIMIT bypasses the counter check
        semaphore.tryAcquire();
        semaphore.acquire();

        // acquireCount is 2, limit is 0 → availablePermits = 0 - 2 = -2
        assertEquals((-2), semaphore.getAvailablePermits());
    }
}
