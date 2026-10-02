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
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor0 = new ScheduledThreadPoolExecutor(4507);
        TimeUnit timeUnit0 = TimeUnit.MINUTES;
        TimedSemaphore timedSemaphore0 = new TimedSemaphore(scheduledThreadPoolExecutor0, 4507, timeUnit0, (-2399));
        timedSemaphore0.endOfPeriod();
        timedSemaphore0.shutdown();
        timedSemaphore0.getAcquireCount();
        timedSemaphore0.shutdown();
        timedSemaphore0.getLimit();
        TimedSemaphore.Builder timedSemaphore_Builder0 = new TimedSemaphore.Builder();
        TimedSemaphore timedSemaphore1 = null;
        try {
            timedSemaphore1 = new TimedSemaphore((-1), timeUnit0, 1);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Time period must be greater than 0.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
