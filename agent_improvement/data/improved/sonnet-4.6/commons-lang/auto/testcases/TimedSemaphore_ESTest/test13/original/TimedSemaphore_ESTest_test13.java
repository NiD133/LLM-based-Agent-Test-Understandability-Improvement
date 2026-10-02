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
        TimeUnit timeUnit0 = TimeUnit.HOURS;
        TimedSemaphore timedSemaphore0 = new TimedSemaphore(1228L, timeUnit0, (-2));
        timedSemaphore0.endOfPeriod();
        timedSemaphore0.tryAcquire();
        timedSemaphore0.shutdown();
        timedSemaphore0.shutdown();
        TimedSemaphore.builder();
        TimeUnit timeUnit1 = TimeUnit.NANOSECONDS;
        TimedSemaphore timedSemaphore1 = null;
        try {
            timedSemaphore1 = new TimedSemaphore((ScheduledExecutorService) null, (-1229L), timeUnit1, (-745));
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Time period must be greater than 0.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
