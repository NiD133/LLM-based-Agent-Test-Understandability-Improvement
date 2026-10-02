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
public class TimedSemaphore_ESTest_test00 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        TimeUnit timeUnit0 = TimeUnit.MICROSECONDS;
        TimedSemaphore timedSemaphore0 = new TimedSemaphore(9223372036854775807L, timeUnit0, 1250);
        timedSemaphore0.acquire();
        timedSemaphore0.startTimer();
        TimedSemaphore.Builder timedSemaphore_Builder0 = TimedSemaphore.builder();
        timedSemaphore0.tryAcquire();
        timedSemaphore0.getLimit();
        timedSemaphore0.acquire();
        timedSemaphore_Builder0.setPeriod(0L);
        TimedSemaphore.builder();
        TimeUnit timeUnit1 = TimeUnit.HOURS;
        TimedSemaphore timedSemaphore1 = new TimedSemaphore(1239L, timeUnit1, 1);
        timedSemaphore1.shutdown();
        timedSemaphore0.acquire();
        timedSemaphore1.getAverageCallsPerPeriod();
        // Undeclared exception!
        try {
            timedSemaphore1.acquire();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // TimedSemaphore is shut down.
            //
            verifyException("org.apache.commons.lang3.concurrent.TimedSemaphore", e);
        }
    }
}
