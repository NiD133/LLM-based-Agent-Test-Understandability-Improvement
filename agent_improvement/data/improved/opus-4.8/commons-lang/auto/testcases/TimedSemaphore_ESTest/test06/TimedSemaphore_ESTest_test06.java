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
public class TimedSemaphore_ESTest_test06 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Exercises the lifecycle of a TimedSemaphore that is created with no limit
     * (limit == 0): ending periods, starting the timer, changing the limit and
     * finally shutting it down. Afterwards a brand-new semaphore is produced via
     * the Builder to confirm the builder path also works.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Create an unlimited semaphore (limit 0) with a 3049-microsecond period.
        TimedSemaphore semaphore = new TimedSemaphore(3049L, TimeUnit.MICROSECONDS, 0);

        // Signal the end of two consecutive periods before any acquire() happened.
        semaphore.endOfPeriod();
        semaphore.endOfPeriod();

        // Start the periodic timer task that monitors the time frame.
        semaphore.startTimer();

        // Re-affirm the "no limit" setting, then close out another period.
        semaphore.setLimit(0);
        semaphore.endOfPeriod();

        // Query the running average of acquire() calls per period.
        semaphore.getAverageCallsPerPeriod();

        // Shut the semaphore down; a negative limit afterwards is still accepted.
        semaphore.shutdown();
        semaphore.setLimit(-1033);

        // Independently, build a fresh semaphore through the Builder with a 36-unit period.
        TimedSemaphore.Builder builder = TimedSemaphore.builder();
        TimedSemaphore built = builder.setPeriod(36L).get();
        assertNotNull(built);
    }
}
