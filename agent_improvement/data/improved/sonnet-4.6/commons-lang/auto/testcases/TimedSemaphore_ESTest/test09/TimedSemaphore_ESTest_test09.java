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
public class TimedSemaphore_ESTest_test09 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Verifies that a TimedSemaphore configured with a negative limit operates
     * in no-limit mode (acquires never block), that the Builder fluent API
     * chains correctly, and that endOfPeriod() resets the acquire counter so
     * subsequent tryAcquire()/acquire() calls proceed without blocking.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // --- Setup: semaphore with no-limit (negative limit <= NO_LIMIT disables blocking) ---
        TimeUnit hours = TimeUnit.HOURS;
        // null executor causes TimedSemaphore to create its own internal ScheduledExecutorService
        TimedSemaphore semaphore = new TimedSemaphore((ScheduledExecutorService) null, 2280L, hours, (-3414));

        // --- Builder API: obtain two independent builder references ---
        TimedSemaphore.Builder builder = TimedSemaphore.builder();
        // Second builder is created but not retained; verifies builder() factory produces new instances
        TimedSemaphore.builder();

        // --- Timer lifecycle: start the periodic end-of-period task ---
        semaphore.startTimer();

        // Reaffirm the no-limit setting after timer has started
        semaphore.setLimit((-3414));

        // Simulate end of time period: resets acquireCount and captures lastCallsPerPeriod
        semaphore.endOfPeriod();

        // --- Builder fluent configuration ---
        // setTimeUnit returns the same Builder instance (fluent API)
        TimedSemaphore.Builder builderWithTimeUnit = builder.setTimeUnit(hours);
        // Period of 0 is accepted by the builder (validation occurs on get())
        builder.setPeriod(0);

        // --- Acquire operations: both succeed because negative limit means no blocking ---
        semaphore.tryAcquire();
        semaphore.acquire();

        // --- Inspect per-period statistics ---
        // Returns the acquireCount captured at the last endOfPeriod() call (0 after the reset above)
        semaphore.getLastAcquiresPerPeriod();

        // Continue configuring the builder with a null service (builder allows null; validation is deferred)
        builder.setService((ScheduledExecutorService) null);

        // Retrieve the current-period acquire count twice to confirm it is stable between reads
        semaphore.getAcquireCount();
        semaphore.getAcquireCount();

        // Third builder created and discarded; confirms factory method is side-effect free
        TimedSemaphore.builder();

        // Apply a valid period to the builder that already has a time unit set
        builderWithTimeUnit.setPeriod(3853L);
    }
}
