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

    /**
     * Verifies that calling startTimer() on a shutdown TimedSemaphore throws
     * RejectedExecutionException, because shutdown() terminates the underlying
     * ScheduledExecutorService which then rejects new task submissions via AbortPolicy.
     *
     * The builder is mutated through multiple chained calls (all aliases point to the
     * same builder instance due to the fluent API returning {@code this}). The last
     * setTimeUnit(DAYS) call wins, so the semaphore is built with period=261, unit=DAYS.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Create a builder and configure it through chained calls.
        // All builder references below point to the same underlying builder instance.
        TimedSemaphore.Builder builder = TimedSemaphore.builder();

        // Set time unit to DAYS; the fluent setter returns the same builder instance.
        TimedSemaphore.Builder builderAfterDays = builder.setTimeUnit(TimeUnit.DAYS);

        // Override the time unit to SECONDS on the original reference.
        builder.setTimeUnit(TimeUnit.SECONDS);

        // Set the period to 261; the builder now has period=261, unit=SECONDS.
        TimedSemaphore.Builder builderAfterPeriod = builderAfterDays.setPeriod(261L);

        // Override the time unit back to DAYS; the builder now has period=261, unit=DAYS.
        TimedSemaphore.Builder builderAfterFinalUnit = builderAfterPeriod.setTimeUnit(TimeUnit.DAYS);

        // Build the semaphore from the configured builder (period=261, unit=DAYS).
        TimedSemaphore semaphore = builderAfterPeriod.get();

        // Acquire a permit (non-blocking) and then shut down the semaphore.
        // Shutdown terminates the internal ScheduledExecutorService.
        semaphore.tryAcquire();
        semaphore.shutdown();

        // Reconfigure the builder and build additional semaphore instances;
        // these calls also verify that the builder remains usable after a semaphore is shut down.
        builderAfterFinalUnit.setLimit(0);
        semaphore.setLimit(1180);
        builderAfterFinalUnit.get();

        builder.setTimeUnit(TimeUnit.DAYS);
        builderAfterFinalUnit.setPeriod(261L);

        // No period has completed (shutdown happened before the first timer tick),
        // so getLastAcquiresPerPeriod() returns 0.
        semaphore.getLastAcquiresPerPeriod();

        builderAfterFinalUnit.setTimeUnit(TimeUnit.DAYS);
        semaphore.setLimit(1180);
        builderAfterFinalUnit.get();

        // startTimer() submits a periodic task to the executor service.
        // Because the semaphore was shut down (executor terminated), this submission
        // is rejected by ThreadPoolExecutor$AbortPolicy, throwing RejectedExecutionException.
        try {
            semaphore.startTimer();
            fail("Expecting exception: RejectedExecutionException");
        } catch (RejectedExecutionException e) {
            verifyException("java.util.concurrent.ThreadPoolExecutor$AbortPolicy", e);
        }
    }
}
