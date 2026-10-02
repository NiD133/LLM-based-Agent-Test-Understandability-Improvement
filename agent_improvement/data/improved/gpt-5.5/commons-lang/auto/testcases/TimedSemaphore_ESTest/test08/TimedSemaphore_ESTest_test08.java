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
public class TimedSemaphore_ESTest_test08 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        TimedSemaphore.Builder reusableBuilder = TimedSemaphore.builder();
        TimeUnit days = TimeUnit.DAYS;
        TimedSemaphore.Builder builderAfterDaysUnit = reusableBuilder.setTimeUnit(days);
        TimeUnit seconds = TimeUnit.SECONDS;
        reusableBuilder.setTimeUnit(seconds);
        TimedSemaphore.Builder builderAfterPeriod = builderAfterDaysUnit.setPeriod(261L);
        TimedSemaphore.Builder builderAfterResetToDays = builderAfterPeriod.setTimeUnit(days);
        TimedSemaphore semaphore = builderAfterPeriod.get();

        semaphore.tryAcquire();
        semaphore.shutdown();
        builderAfterResetToDays.setLimit(0);
        semaphore.setLimit(1180);
        builderAfterResetToDays.get();
        reusableBuilder.setTimeUnit(days);
        builderAfterResetToDays.setPeriod(261L);
        semaphore.getLastAcquiresPerPeriod();
        builderAfterResetToDays.setTimeUnit(days);
        semaphore.setLimit(1180);
        builderAfterResetToDays.get();

        try {
            semaphore.startTimer();
            fail("Expecting exception: RejectedExecutionException");
        } catch (RejectedExecutionException e) {
            verifyException("java.util.concurrent.ThreadPoolExecutor$AbortPolicy", e);
        }
    }
}
