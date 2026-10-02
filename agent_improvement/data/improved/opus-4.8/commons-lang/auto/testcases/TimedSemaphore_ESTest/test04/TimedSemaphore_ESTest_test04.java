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

    /**
     * Builds a TimedSemaphore through its fluent Builder, then verifies that the
     * timer can be started and that the basic accessors return the expected values.
     *
     * <p>Note: the Builder's setter methods all return the same Builder instance,
     * so every {@code builder.setXxx(...)} call below mutates one shared object.</p>
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // A single Builder instance is configured step by step (each setter returns it).
        TimedSemaphore.Builder builder = new TimedSemaphore.Builder();
        builder.setTimeUnit(TimeUnit.HOURS);

        // First executor service: a pool whose ThreadFactory always returns a null thread.
        ThreadFactory nullThreadFactory = mock(ThreadFactory.class, new ViolatedAssumptionAnswer());
        doReturn((Thread) null).when(nullThreadFactory).newThread(any(java.lang.Runnable.class));
        ScheduledThreadPoolExecutor firstExecutor = new ScheduledThreadPoolExecutor(0, nullThreadFactory);

        // Submit a (mock) thread so the executor has work; the chain of MockThread
        // constructors exercises the various Thread constructor overloads.
        ThreadGroup mockThreadGroup = mock(ThreadGroup.class, new ViolatedAssumptionAnswer());
        MockThread baseThread = new MockThread();
        MockThread groupedThread = new MockThread(mockThreadGroup, baseThread, "E*>MF]SL7");
        MockThread namedThread = new MockThread(groupedThread, "+Z-.NN}7l}dv\"f");
        MockThread runnableThread = new MockThread(namedThread);
        firstExecutor.execute(runnableThread);

        // Wire the first executor into the builder, then set a (no-op) limit of 0.
        builder.setService(firstExecutor);
        builder.setLimit(0);
        builder.setLimit(0);

        // Second executor service replaces the first one in the builder.
        ThreadFactory secondNullThreadFactory = mock(ThreadFactory.class, new ViolatedAssumptionAnswer());
        doReturn((Thread) null).when(secondNullThreadFactory).newThread(any(java.lang.Runnable.class));
        ScheduledThreadPoolExecutor secondExecutor = new ScheduledThreadPoolExecutor(2780, secondNullThreadFactory);
        builder.setService(secondExecutor);

        // Period 0 is overwritten by 2780; the constructor requires a period > 0.
        builder.setPeriod(0L);
        builder.setPeriod(2780);

        // Build the semaphore (period = 2780, limit = 0, service = secondExecutor).
        TimedSemaphore timedSemaphore = builder.get();
        builder.setTimeUnit(TimeUnit.HOURS);

        timedSemaphore.startTimer();

        // No acquire() has been called yet, so the acquire count stays at 0.
        assertEquals(0, timedSemaphore.getAcquireCount());
        // The configured period is reported back unchanged.
        assertEquals(2780L, timedSemaphore.getPeriod());
    }
}
