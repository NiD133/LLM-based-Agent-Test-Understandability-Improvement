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
        final TimeUnit hours = TimeUnit.HOURS;
        final int disabledLimit = -3414;

        TimedSemaphore semaphore = new TimedSemaphore((ScheduledExecutorService) null, 2280L, hours, disabledLimit);
        TimedSemaphore.Builder builder = TimedSemaphore.builder();

        TimedSemaphore.builder();
        semaphore.startTimer();
        semaphore.setLimit(disabledLimit);
        semaphore.endOfPeriod();

        TimedSemaphore.Builder builderAfterTimeUnitSet = builder.setTimeUnit(hours);
        builder.setPeriod(0);

        semaphore.tryAcquire();
        semaphore.acquire();
        semaphore.getLastAcquiresPerPeriod();

        builder.setService((ScheduledExecutorService) null);
        semaphore.getAcquireCount();
        semaphore.getAcquireCount();

        TimedSemaphore.builder();
        builderAfterTimeUnitSet.setPeriod(3853L);
    }
}
