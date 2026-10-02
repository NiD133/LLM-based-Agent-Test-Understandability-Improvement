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
public class TimedSemaphore_ESTest_test14 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * When the limit is 0 the semaphore is effectively switched off, so neither
     * tryAcquire() nor acquire() blocks and both increment the acquire count.
     * After two successful acquisitions the available permits are limit - count,
     * i.e. 0 - 2 = -2.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        final int noLimit = 0;
        TimedSemaphore switchedOffSemaphore =
                new TimedSemaphore(1091L, TimeUnit.HOURS, noLimit);

        switchedOffSemaphore.tryAcquire();
        switchedOffSemaphore.acquire();

        assertEquals(-2, switchedOffSemaphore.getAvailablePermits());
    }
}
