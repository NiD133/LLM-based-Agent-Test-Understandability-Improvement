package org.apache.commons.lang3.concurrent;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TimedSemaphore_ESTest_test08 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Verifies that calling {@link TimedSemaphore#startTimer()} after the semaphore
     * has been shut down fails with a {@link RejectedExecutionException}: shutdown
     * terminates the internally-created executor service, so it can no longer accept
     * the periodic timer task.
     */
    @Test(timeout = 4000)
    public void startTimerAfterShutdownIsRejected() throws Throwable {
        // Configure a builder with a valid period (required: must be > 0) and a time unit.
        // The builder is mutated several times below; only the values present at get()
        // time matter, so the intermediate setTimeUnit calls have no lasting effect.
        TimedSemaphore.Builder builder = TimedSemaphore.builder();
        builder.setTimeUnit(TimeUnit.DAYS);
        builder.setTimeUnit(TimeUnit.SECONDS);
        builder.setPeriod(261L);
        builder.setTimeUnit(TimeUnit.DAYS);

        // Build a semaphore. Because no executor service was supplied, it owns an
        // internal ScheduledThreadPoolExecutor that shutdown() will terminate.
        TimedSemaphore semaphore = builder.get();

        // First (non-blocking) acquire; then shut the semaphore down, terminating
        // its executor service.
        semaphore.tryAcquire();
        semaphore.shutdown();

        // Further builder/semaphore reconfiguration that does not affect the
        // already-terminated executor of the existing semaphore.
        builder.setLimit(0);
        semaphore.setLimit(1180);
        builder.get();
        builder.setTimeUnit(TimeUnit.DAYS);
        builder.setPeriod(261L);
        semaphore.getLastAcquiresPerPeriod();
        builder.setTimeUnit(TimeUnit.DAYS);
        semaphore.setLimit(1180);
        builder.get();

        // Scheduling the timer task on the terminated executor is rejected.
        try {
            semaphore.startTimer();
            fail("Expecting exception: RejectedExecutionException");
        } catch (RejectedExecutionException e) {
            // The executor's AbortPolicy rejects the task because the pool is terminated.
            verifyException("java.util.concurrent.ThreadPoolExecutor$AbortPolicy", e);
        }
    }
}
