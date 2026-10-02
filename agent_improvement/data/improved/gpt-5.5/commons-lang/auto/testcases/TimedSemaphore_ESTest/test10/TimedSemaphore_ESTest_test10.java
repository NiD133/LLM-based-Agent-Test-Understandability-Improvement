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
public class TimedSemaphore_ESTest_test10 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        final long period = 4081L;
        final TimeUnit periodUnit = TimeUnit.HOURS;
        final int unlimitedPermitLimit = -1727;

        TimedSemaphore.builder();
        final TimedSemaphore timedSemaphore = new TimedSemaphore(period, periodUnit, unlimitedPermitLimit);

        timedSemaphore.endOfPeriod();
        timedSemaphore.acquire();
    }
}
