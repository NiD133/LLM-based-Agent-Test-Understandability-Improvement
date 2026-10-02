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

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        TimeUnit timeUnit0 = TimeUnit.HOURS;
        TimedSemaphore timedSemaphore0 = new TimedSemaphore(1231L, timeUnit0, 0);
        timedSemaphore0.endOfPeriod();
        timedSemaphore0.tryAcquire();
        timedSemaphore0.shutdown();
        timedSemaphore0.shutdown();
        TimedSemaphore.builder();
        timedSemaphore0.shutdown();
        // Undeclared exception!
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
