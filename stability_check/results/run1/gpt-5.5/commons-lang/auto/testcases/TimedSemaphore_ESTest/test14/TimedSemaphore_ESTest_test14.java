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

    private static final long PERIOD_LENGTH = 1091L;
    private static final TimeUnit PERIOD_UNIT = TimeUnit.HOURS;
    private static final int DISABLED_LIMIT = 0;
    private static final int EXPECTED_AVAILABLE_PERMITS_AFTER_TWO_ACQUIRES = -2;

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        TimedSemaphore semaphoreWithNoLimit = new TimedSemaphore(PERIOD_LENGTH, PERIOD_UNIT, DISABLED_LIMIT);

        semaphoreWithNoLimit.tryAcquire();
        semaphoreWithNoLimit.acquire();

        assertEquals(EXPECTED_AVAILABLE_PERMITS_AFTER_TWO_ACQUIRES, semaphoreWithNoLimit.getAvailablePermits());
    }
}
