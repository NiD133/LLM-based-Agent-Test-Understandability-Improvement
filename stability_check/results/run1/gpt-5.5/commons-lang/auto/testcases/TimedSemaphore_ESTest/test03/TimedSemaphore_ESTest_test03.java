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
public class TimedSemaphore_ESTest_test03 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        TimeUnit validTimeUnit = TimeUnit.HOURS;
        long validPeriod = 1228L;
        int validLimit = 8;
        TimedSemaphore activeSemaphore = new TimedSemaphore(validPeriod, validTimeUnit, validLimit);

        activeSemaphore.endOfPeriod();
        activeSemaphore.startTimer();
        activeSemaphore.tryAcquire();
        activeSemaphore.shutdown();
        activeSemaphore.shutdown();

        TimedSemaphore.builder();

        ScheduledExecutorService noExecutorService = null;
        TimeUnit invalidTimeUnit = TimeUnit.NANOSECONDS;
        long invalidPeriod = -1229L;
        int invalidLimit = -745;
        TimedSemaphore invalidSemaphore = null;
        try {
            invalidSemaphore = new TimedSemaphore(noExecutorService, invalidPeriod, invalidTimeUnit, invalidLimit);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Time period must be greater than 0.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
