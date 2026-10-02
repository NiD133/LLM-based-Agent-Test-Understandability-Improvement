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
        TimedSemaphore.Builder timedSemaphore_Builder0 = new TimedSemaphore.Builder();
        timedSemaphore_Builder0.setPeriod(2475L);
        timedSemaphore_Builder0.setLimit(328);
        TimedSemaphore timedSemaphore0 = timedSemaphore_Builder0.get();
        timedSemaphore0.shutdown();
        timedSemaphore0.endOfPeriod();
        TimeUnit timeUnit0 = TimeUnit.MINUTES;
        timedSemaphore_Builder0.setTimeUnit(timeUnit0);
        timedSemaphore0.getAvailablePermits();
        timedSemaphore_Builder0.setLimit(0);
        TimedSemaphore.Builder timedSemaphore_Builder1 = timedSemaphore_Builder0.setLimit((-1246));
        timedSemaphore0.getAvailablePermits();
        timedSemaphore_Builder0.setPeriod(328);
        timedSemaphore0.getLimit();
        TimedSemaphore timedSemaphore1 = timedSemaphore_Builder1.get();
        timedSemaphore0.getAvailablePermits();
        timedSemaphore0.shutdown();
        timedSemaphore_Builder1.get();
        timedSemaphore0.getAvailablePermits();
        timedSemaphore_Builder1.get();
        timedSemaphore_Builder1.setLimit(328);
        timedSemaphore_Builder1.setPeriod(0);
        timedSemaphore_Builder1.setService((ScheduledExecutorService) null);
        timedSemaphore0.getAcquireCount();
        timedSemaphore1.getUnit();
        // Undeclared exception!
        try {
            timedSemaphore_Builder0.get();
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Time period must be greater than 0.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
