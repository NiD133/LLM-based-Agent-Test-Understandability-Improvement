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
     * Verifies that getAvailablePermits() returns a negative value when both tryAcquire()
     * and acquire() are called on a semaphore with NO_LIMIT (limit=0).
     *
     * With NO_LIMIT, permits are never blocked, so the internal counter keeps incrementing
     * past the limit. After two acquisitions, availablePermits = limit(0) - acquireCount(2) = -2.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // Create a semaphore with NO_LIMIT (0) so that acquisitions are never blocked
        TimedSemaphore semaphore = new TimedSemaphore(1091L, TimeUnit.HOURS, TimedSemaphore.NO_LIMIT);

        // Both calls succeed immediately because NO_LIMIT disables blocking
        semaphore.tryAcquire(); // acquireCount becomes 1
        semaphore.acquire();    // acquireCount becomes 2

        // availablePermits = limit(0) - acquireCount(2) = -2
        assertEquals(-2, semaphore.getAvailablePermits());
    }
}
