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
public class TimedSemaphore_ESTest_test17 extends TimedSemaphore_ESTest_scaffolding {

    private static final long NON_POSITIVE_PERIOD = 0L;
    private static final int LIMIT = 465;
    private static final String EXPECTED_EXCEPTION_MESSAGE = "Expecting exception: IllegalArgumentException";
    private static final String VALIDATION_CLASS = "org.apache.commons.lang3.Validate";

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        TimedSemaphore.builder();
        TimeUnit periodUnit = TimeUnit.SECONDS;
        try {
            new TimedSemaphore(NON_POSITIVE_PERIOD, periodUnit, LIMIT);
            fail(EXPECTED_EXCEPTION_MESSAGE);
        } catch (IllegalArgumentException e) {
            verifyException(VALIDATION_CLASS, e);
        }
    }
}
