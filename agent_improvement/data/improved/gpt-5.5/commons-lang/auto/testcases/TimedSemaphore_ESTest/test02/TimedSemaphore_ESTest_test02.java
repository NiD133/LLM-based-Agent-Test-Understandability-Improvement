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
        final TimedSemaphore.Builder builder = TimedSemaphore.builder();
        assertNotNull(builder);

        final TimeUnit hours = TimeUnit.HOURS;
        final TimedSemaphore semaphoreWithNegativeLimit = new TimedSemaphore(4081L, hours, (-1727));

        semaphoreWithNegativeLimit.tryAcquire();
        semaphoreWithNegativeLimit.getLimit();

        builder.setPeriod(0L);

        semaphoreWithNegativeLimit.getAvailablePermits();
        semaphoreWithNegativeLimit.tryAcquire();

        builder.setTimeUnit(hours);

        final TimeUnit milliseconds = TimeUnit.MILLISECONDS;
        final TimedSemaphore semaphoreWithoutExecutor = new TimedSemaphore((ScheduledExecutorService) null, 1L, milliseconds, 0);

        semaphoreWithoutExecutor.getUnit();
        semaphoreWithNegativeLimit.isShutdown();
        semaphoreWithoutExecutor.getPeriod();
    }
}
