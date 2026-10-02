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
public class TimedSemaphore_ESTest_test05 extends TimedSemaphore_ESTest_scaffolding {

    private static final long PERIOD = 3049L;
    private static final int INITIAL_LIMIT = 0;
    private static final int UPDATED_LIMIT_AFTER_SHUTDOWN = -1021;

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        TimeUnit periodUnit = TimeUnit.MICROSECONDS;
        TimedSemaphore semaphore = new TimedSemaphore(PERIOD, periodUnit, INITIAL_LIMIT);

        advanceInitialPeriodsAndStartTimer(semaphore);
        switchSemaphoreToNoLimitAndRecordAverage(semaphore);
        shutdownAndSetPostShutdownLimit(semaphore);

        try {
            semaphore.acquire();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.lang3.concurrent.TimedSemaphore", e);
        }
    }

    private void advanceInitialPeriodsAndStartTimer(TimedSemaphore semaphore) {
        semaphore.endOfPeriod();
        semaphore.endOfPeriod();
        TimedSemaphore.builder();
        semaphore.startTimer();
    }

    private void switchSemaphoreToNoLimitAndRecordAverage(TimedSemaphore semaphore) {
        semaphore.setLimit(INITIAL_LIMIT);
        TimedSemaphore.builder();
        semaphore.endOfPeriod();
        semaphore.getAverageCallsPerPeriod();
    }

    private void shutdownAndSetPostShutdownLimit(TimedSemaphore semaphore) {
        semaphore.shutdown();
        semaphore.setLimit(UPDATED_LIMIT_AFTER_SHUTDOWN);
    }
}
