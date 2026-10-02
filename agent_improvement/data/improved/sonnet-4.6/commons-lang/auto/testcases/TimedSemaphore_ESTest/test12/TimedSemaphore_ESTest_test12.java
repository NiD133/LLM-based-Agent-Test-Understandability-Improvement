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
public class TimedSemaphore_ESTest_test12 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Verifies that calling acquire() on a shut-down TimedSemaphore throws IllegalStateException.
     * Also confirms that shutdown() is idempotent (safe to call multiple times).
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // Create a semaphore with NO_LIMIT (0), so acquire/tryAcquire never block
        TimedSemaphore timedSemaphore0 = new TimedSemaphore(1231L, TimeUnit.HOURS, 0);

        // Simulate end-of-period reset, then successfully acquire without blocking
        timedSemaphore0.endOfPeriod();
        timedSemaphore0.tryAcquire();

        // Shut down; subsequent shutdown() calls must be no-ops (idempotent)
        timedSemaphore0.shutdown();
        timedSemaphore0.shutdown();

        // Verify builder() is accessible as a static factory (return value unused here)
        TimedSemaphore.builder();

        // Third shutdown() call – still must be a no-op
        timedSemaphore0.shutdown();

        // After shutdown, acquire() must throw IllegalStateException
        try {
            timedSemaphore0.acquire();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // TimedSemaphore is shut down.
            //
            verifyException("org.apache.commons.lang3.concurrent.TimedSemaphore", e);
        }
    }
}
