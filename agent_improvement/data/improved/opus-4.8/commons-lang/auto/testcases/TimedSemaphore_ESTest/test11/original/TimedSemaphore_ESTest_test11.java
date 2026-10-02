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
public class TimedSemaphore_ESTest_test11 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        TimeUnit timeUnit0 = TimeUnit.NANOSECONDS;
        TimedSemaphore timedSemaphore0 = new TimedSemaphore(1L, timeUnit0, 621);
        timedSemaphore0.setLimit(621);
        timedSemaphore0.shutdown();
        TimedSemaphore.Builder timedSemaphore_Builder0 = new TimedSemaphore.Builder();
        TimedSemaphore.Builder timedSemaphore_Builder1 = timedSemaphore_Builder0.setLimit(621);
        ThreadPoolExecutor.DiscardPolicy threadPoolExecutor_DiscardPolicy0 = new ThreadPoolExecutor.DiscardPolicy();
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor0 = new ScheduledThreadPoolExecutor(0, threadPoolExecutor_DiscardPolicy0);
        TimedSemaphore.Builder timedSemaphore_Builder2 = timedSemaphore_Builder1.setService(scheduledThreadPoolExecutor0);
        timedSemaphore_Builder2.setLimit(621);
    }
}
