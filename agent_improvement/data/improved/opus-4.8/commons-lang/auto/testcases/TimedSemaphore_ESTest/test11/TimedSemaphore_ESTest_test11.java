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
public class TimedSemaphore_ESTest_test11 extends TimedSemaphore_ESTest_scaffolding {

    private static final int LIMIT = 621;

    /**
     * Exercises configuring the limit through both APIs:
     * <ul>
     *   <li>on a directly constructed {@link TimedSemaphore} (including after it has been shut down), and</li>
     *   <li>on a {@link TimedSemaphore.Builder} that also has a custom executor service installed.</li>
     * </ul>
     * None of these calls should throw.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Configure the limit on a live semaphore, then again after shutdown.
        TimedSemaphore semaphore = new TimedSemaphore(1L, TimeUnit.NANOSECONDS, LIMIT);
        semaphore.setLimit(LIMIT);
        semaphore.shutdown();

        // Configure a builder: set the limit, attach a custom executor, then set the limit again.
        TimedSemaphore.Builder builder = new TimedSemaphore.Builder();
        builder.setLimit(LIMIT);

        ThreadPoolExecutor.DiscardPolicy discardPolicy = new ThreadPoolExecutor.DiscardPolicy();
        ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(0, discardPolicy);
        builder.setService(executorService);

        builder.setLimit(LIMIT);
    }
}
