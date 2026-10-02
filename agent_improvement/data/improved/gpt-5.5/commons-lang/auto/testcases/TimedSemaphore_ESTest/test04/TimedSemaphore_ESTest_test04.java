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
public class TimedSemaphore_ESTest_test04 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        TimedSemaphore.Builder builder = new TimedSemaphore.Builder();
        TimeUnit configuredUnit = TimeUnit.HOURS;
        TimedSemaphore.Builder builderWithUnit = builder.setTimeUnit(configuredUnit);

        ThreadFactory nullReturningFactory = mock(ThreadFactory.class, new ViolatedAssumptionAnswer());
        doReturn((Thread) null).when(nullReturningFactory).newThread(any(java.lang.Runnable.class));
        ScheduledThreadPoolExecutor executorWithNoWorkers = new ScheduledThreadPoolExecutor(0, nullReturningFactory);

        ThreadGroup threadGroup = mock(ThreadGroup.class, new ViolatedAssumptionAnswer());
        MockThread baseThread = new MockThread();
        MockThread groupedThread = new MockThread(threadGroup, baseThread, "E*>MF]SL7");
        MockThread namedThread = new MockThread(groupedThread, "+Z-.NN}7l}dv\"f");
        MockThread runnableThread = new MockThread(namedThread);
        executorWithNoWorkers.execute(runnableThread);

        builderWithUnit.setService(executorWithNoWorkers);
        TimedSemaphore.Builder builderWithNoLimit = builderWithUnit.setLimit(0);

        ThreadFactory secondNullReturningFactory = mock(ThreadFactory.class, new ViolatedAssumptionAnswer());
        doReturn((Thread) null).when(secondNullReturningFactory).newThread(any(java.lang.Runnable.class));
        builderWithNoLimit.setLimit(0);
        ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(2780, secondNullReturningFactory);
        TimedSemaphore.Builder builderWithExecutor = builderWithNoLimit.setService(scheduledExecutor);

        builderWithNoLimit.setPeriod(0L);
        TimedSemaphore.Builder builderReadyToCreateSemaphore = builderWithExecutor.setPeriod(2780);
        TimedSemaphore timedSemaphore = builderReadyToCreateSemaphore.get();

        builderReadyToCreateSemaphore.setTimeUnit(configuredUnit);
        timedSemaphore.startTimer();
        timedSemaphore.getAcquireCount();
        timedSemaphore.getPeriod();
    }
}
