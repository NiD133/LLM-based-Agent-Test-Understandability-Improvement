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
public class TimedSemaphore_ESTest_test04 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        TimedSemaphore.Builder timedSemaphore_Builder0 = new TimedSemaphore.Builder();
        TimeUnit timeUnit0 = TimeUnit.HOURS;
        TimedSemaphore.Builder timedSemaphore_Builder1 = timedSemaphore_Builder0.setTimeUnit(timeUnit0);
        ThreadFactory threadFactory0 = mock(ThreadFactory.class, new ViolatedAssumptionAnswer());
        doReturn((Thread) null).when(threadFactory0).newThread(any(java.lang.Runnable.class));
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor0 = new ScheduledThreadPoolExecutor(0, threadFactory0);
        ThreadGroup threadGroup0 = mock(ThreadGroup.class, new ViolatedAssumptionAnswer());
        MockThread mockThread0 = new MockThread();
        MockThread mockThread1 = new MockThread(threadGroup0, mockThread0, "E*>MF]SL7");
        MockThread mockThread2 = new MockThread(mockThread1, "+Z-.NN}7l}dv\"f");
        MockThread mockThread3 = new MockThread(mockThread2);
        scheduledThreadPoolExecutor0.execute(mockThread3);
        timedSemaphore_Builder1.setService(scheduledThreadPoolExecutor0);
        TimedSemaphore.Builder timedSemaphore_Builder2 = timedSemaphore_Builder1.setLimit(0);
        ThreadFactory threadFactory1 = mock(ThreadFactory.class, new ViolatedAssumptionAnswer());
        doReturn((Thread) null).when(threadFactory1).newThread(any(java.lang.Runnable.class));
        timedSemaphore_Builder2.setLimit(0);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor1 = new ScheduledThreadPoolExecutor(2780, threadFactory1);
        TimedSemaphore.Builder timedSemaphore_Builder3 = timedSemaphore_Builder2.setService(scheduledThreadPoolExecutor1);
        timedSemaphore_Builder2.setPeriod(0L);
        TimedSemaphore.Builder timedSemaphore_Builder4 = timedSemaphore_Builder3.setPeriod(2780);
        TimedSemaphore timedSemaphore0 = timedSemaphore_Builder4.get();
        timedSemaphore_Builder4.setTimeUnit(timeUnit0);
        timedSemaphore0.startTimer();
        timedSemaphore0.getAcquireCount();
        timedSemaphore0.getPeriod();
    }
}
