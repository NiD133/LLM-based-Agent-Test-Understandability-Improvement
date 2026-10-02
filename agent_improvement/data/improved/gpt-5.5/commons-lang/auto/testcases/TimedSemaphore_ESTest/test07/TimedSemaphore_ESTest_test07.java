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
public class TimedSemaphore_ESTest_test07 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4507);
        final TimeUnit timeUnit = TimeUnit.MINUTES;

        final TimedSemaphore semaphoreWithNegativeLimit = new TimedSemaphore(executor, 4507, timeUnit, (-2399));
        semaphoreWithNegativeLimit.endOfPeriod();
        semaphoreWithNegativeLimit.shutdown();
        semaphoreWithNegativeLimit.getAcquireCount();
        semaphoreWithNegativeLimit.shutdown();
        semaphoreWithNegativeLimit.getLimit();

        final TimedSemaphore.Builder unusedBuilder = new TimedSemaphore.Builder();
        TimedSemaphore semaphoreWithInvalidPeriod = null;
        try {
            semaphoreWithInvalidPeriod = new TimedSemaphore((-1), timeUnit, 1);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Time period must be greater than 0.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
