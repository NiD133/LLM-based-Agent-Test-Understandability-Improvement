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
public class TimedSemaphore_ESTest_test09 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        TimeUnit timeUnit0 = TimeUnit.HOURS;
        TimedSemaphore timedSemaphore0 = new TimedSemaphore((ScheduledExecutorService) null, 2280L, timeUnit0, (-3414));
        TimedSemaphore.Builder timedSemaphore_Builder0 = TimedSemaphore.builder();
        TimedSemaphore.builder();
        timedSemaphore0.startTimer();
        timedSemaphore0.setLimit((-3414));
        timedSemaphore0.endOfPeriod();
        TimedSemaphore.Builder timedSemaphore_Builder1 = timedSemaphore_Builder0.setTimeUnit(timeUnit0);
        timedSemaphore_Builder0.setPeriod(0);
        timedSemaphore0.tryAcquire();
        timedSemaphore0.acquire();
        timedSemaphore0.getLastAcquiresPerPeriod();
        timedSemaphore_Builder0.setService((ScheduledExecutorService) null);
        timedSemaphore0.getAcquireCount();
        timedSemaphore0.getAcquireCount();
        TimedSemaphore.builder();
        timedSemaphore_Builder1.setPeriod(3853L);
    }
}
