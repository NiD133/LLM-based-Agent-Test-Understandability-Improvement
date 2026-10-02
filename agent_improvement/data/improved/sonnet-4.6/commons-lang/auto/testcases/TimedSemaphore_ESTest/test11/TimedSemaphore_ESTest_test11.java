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

    @Test(timeout = 4000)
    public void test_builderSetLimitAndServiceAfterDirectSemaphoreShutdown() throws Throwable {
        // Create and shut down a TimedSemaphore using the deprecated constructor
        TimedSemaphore semaphore = new TimedSemaphore(1L, TimeUnit.NANOSECONDS, 621);
        semaphore.setLimit(621);
        semaphore.shutdown();

        // Build a new TimedSemaphore configuration using the Builder API,
        // attaching a custom ScheduledThreadPoolExecutor with a discard policy
        TimedSemaphore.Builder builder = new TimedSemaphore.Builder();
        TimedSemaphore.Builder builderWithLimit = builder.setLimit(621);

        ThreadPoolExecutor.DiscardPolicy discardPolicy = new ThreadPoolExecutor.DiscardPolicy();
        ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(0, discardPolicy);

        TimedSemaphore.Builder builderWithService = builderWithLimit.setService(scheduledExecutor);
        builderWithService.setLimit(621);
    }
}
