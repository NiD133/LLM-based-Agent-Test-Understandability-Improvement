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
public class TimedSemaphore_ESTest_test08 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        TimedSemaphore.Builder timedSemaphore_Builder0 = TimedSemaphore.builder();
        TimeUnit timeUnit0 = TimeUnit.DAYS;
        TimedSemaphore.Builder timedSemaphore_Builder1 = timedSemaphore_Builder0.setTimeUnit(timeUnit0);
        TimeUnit timeUnit1 = TimeUnit.SECONDS;
        timedSemaphore_Builder0.setTimeUnit(timeUnit1);
        TimedSemaphore.Builder timedSemaphore_Builder2 = timedSemaphore_Builder1.setPeriod(261L);
        TimedSemaphore.Builder timedSemaphore_Builder3 = timedSemaphore_Builder2.setTimeUnit(timeUnit0);
        TimedSemaphore timedSemaphore0 = timedSemaphore_Builder2.get();
        timedSemaphore0.tryAcquire();
        timedSemaphore0.shutdown();
        timedSemaphore_Builder3.setLimit(0);
        timedSemaphore0.setLimit(1180);
        timedSemaphore_Builder3.get();
        timedSemaphore_Builder0.setTimeUnit(timeUnit0);
        timedSemaphore_Builder3.setPeriod(261L);
        timedSemaphore0.getLastAcquiresPerPeriod();
        timedSemaphore_Builder3.setTimeUnit(timeUnit0);
        timedSemaphore0.setLimit(1180);
        timedSemaphore_Builder3.get();
        // Undeclared exception!
        try {
            timedSemaphore0.startTimer();
            fail("Expecting exception: RejectedExecutionException");
        } catch (RejectedExecutionException e) {
            //
            // Task java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask@de466 rejected from java.util.concurrent.ScheduledThreadPoolExecutor@7ad84644[Terminated, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0]
            //
            verifyException("java.util.concurrent.ThreadPoolExecutor$AbortPolicy", e);
        }
    }
}
