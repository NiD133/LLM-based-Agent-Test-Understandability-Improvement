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
        TimeUnit microseconds = TimeUnit.MICROSECONDS;
        TimedSemaphore highLimitSemaphore = new TimedSemaphore(9223372036854775807L, microseconds, 1250);

        highLimitSemaphore.acquire();
        highLimitSemaphore.startTimer();

        TimedSemaphore.Builder builder = TimedSemaphore.builder();
        highLimitSemaphore.tryAcquire();
        highLimitSemaphore.getLimit();
        highLimitSemaphore.acquire();

        builder.setPeriod(0L);
        TimedSemaphore.builder();

        TimeUnit hours = TimeUnit.HOURS;
        TimedSemaphore shutdownSemaphore = new TimedSemaphore(1239L, hours, 1);
        shutdownSemaphore.shutdown();

        highLimitSemaphore.acquire();
        shutdownSemaphore.getAverageCallsPerPeriod();

        // Undeclared exception!
        try {
            shutdownSemaphore.acquire();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // TimedSemaphore is shut down.
            //
            verifyException("org.apache.commons.lang3.concurrent.TimedSemaphore", e);
        }
    }
}
