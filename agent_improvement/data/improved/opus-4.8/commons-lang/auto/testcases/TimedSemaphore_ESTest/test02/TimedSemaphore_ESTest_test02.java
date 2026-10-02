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

    /**
     * Exercises the read-only accessors and the {@code tryAcquire()} flow on two
     * {@link TimedSemaphore} instances, while independently configuring a
     * {@link TimedSemaphore.Builder}. The builder is configured but never built,
     * so it has no effect on the semaphores created via the deprecated constructors.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // A standalone builder used only to verify that its fluent setters work.
        TimedSemaphore.Builder builder = TimedSemaphore.builder();
        assertNotNull(builder);

        // Semaphore with a negative limit, which disables the limit (treated as "no limit").
        TimedSemaphore unlimitedSemaphore = new TimedSemaphore(4081L, TimeUnit.HOURS, -1727);
        unlimitedSemaphore.tryAcquire();
        unlimitedSemaphore.getLimit();

        builder.setPeriod(0L);

        unlimitedSemaphore.getAvailablePermits();
        unlimitedSemaphore.tryAcquire();

        builder.setTimeUnit(TimeUnit.HOURS);

        // Second semaphore built with an explicit (null) executor service and a zero limit.
        TimedSemaphore zeroLimitSemaphore =
                new TimedSemaphore((ScheduledExecutorService) null, 1L, TimeUnit.MILLISECONDS, 0);
        zeroLimitSemaphore.getUnit();
        unlimitedSemaphore.isShutdown();
        zeroLimitSemaphore.getPeriod();
    }
}
