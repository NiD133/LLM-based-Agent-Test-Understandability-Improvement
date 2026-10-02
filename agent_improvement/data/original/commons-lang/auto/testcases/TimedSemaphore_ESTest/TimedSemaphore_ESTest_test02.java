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
public class TimedSemaphore_ESTest_test02 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        TimedSemaphore.Builder timedSemaphore_Builder0 = TimedSemaphore.builder();
        assertNotNull(timedSemaphore_Builder0);
        TimeUnit timeUnit0 = TimeUnit.HOURS;
        TimedSemaphore timedSemaphore0 = new TimedSemaphore(4081L, timeUnit0, (-1727));
        timedSemaphore0.tryAcquire();
        timedSemaphore0.getLimit();
        timedSemaphore_Builder0.setPeriod(0L);
        timedSemaphore0.getAvailablePermits();
        timedSemaphore0.tryAcquire();
        timedSemaphore_Builder0.setTimeUnit(timeUnit0);
        TimeUnit timeUnit1 = TimeUnit.MILLISECONDS;
        TimedSemaphore timedSemaphore1 = new TimedSemaphore((ScheduledExecutorService) null, 1L, timeUnit1, 0);
        timedSemaphore1.getUnit();
        timedSemaphore0.isShutdown();
        timedSemaphore1.getPeriod();
    }
}
