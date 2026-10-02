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
public class TimedSemaphore_ESTest_test01 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        TimedSemaphore.Builder reusableBuilder = new TimedSemaphore.Builder();
        reusableBuilder.setPeriod(2475L);
        reusableBuilder.setLimit(328);

        TimedSemaphore shutdownSemaphore = reusableBuilder.get();
        shutdownSemaphore.shutdown();
        shutdownSemaphore.endOfPeriod();

        TimeUnit configuredUnit = TimeUnit.MINUTES;
        reusableBuilder.setTimeUnit(configuredUnit);
        shutdownSemaphore.getAvailablePermits();

        reusableBuilder.setLimit(0);
        TimedSemaphore.Builder sameBuilderWithNegativeLimit = reusableBuilder.setLimit((-1246));
        shutdownSemaphore.getAvailablePermits();

        reusableBuilder.setPeriod(328);
        shutdownSemaphore.getLimit();

        TimedSemaphore negativeLimitSemaphore = sameBuilderWithNegativeLimit.get();
        shutdownSemaphore.getAvailablePermits();
        shutdownSemaphore.shutdown();

        sameBuilderWithNegativeLimit.get();
        shutdownSemaphore.getAvailablePermits();
        sameBuilderWithNegativeLimit.get();

        sameBuilderWithNegativeLimit.setLimit(328);
        sameBuilderWithNegativeLimit.setPeriod(0);
        sameBuilderWithNegativeLimit.setService((ScheduledExecutorService) null);

        shutdownSemaphore.getAcquireCount();
        negativeLimitSemaphore.getUnit();

        try {
            reusableBuilder.get();
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
