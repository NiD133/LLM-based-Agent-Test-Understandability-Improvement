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
public class TimedSemaphore_ESTest_test13 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        TimeUnit hourPeriodUnit = TimeUnit.HOURS;
        TimedSemaphore semaphoreWithNoLimit = new TimedSemaphore(1228L, hourPeriodUnit, (-2));

        semaphoreWithNoLimit.endOfPeriod();
        semaphoreWithNoLimit.tryAcquire();
        semaphoreWithNoLimit.shutdown();
        semaphoreWithNoLimit.shutdown();

        TimedSemaphore.builder();

        TimeUnit nanosecondPeriodUnit = TimeUnit.NANOSECONDS;
        TimedSemaphore semaphoreWithInvalidPeriod = null;
        try {
            semaphoreWithInvalidPeriod = new TimedSemaphore((ScheduledExecutorService) null, (-1229L), nanosecondPeriodUnit, (-745));
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Time period must be greater than 0.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
