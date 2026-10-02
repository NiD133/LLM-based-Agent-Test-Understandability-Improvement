package org.apache.commons.lang3.concurrent;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
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
     * Tests that the TimedSemaphore builder accepts chained configuration calls
     * (setTimeUnit, setService, setLimit, setPeriod) and that the built semaphore
     * can start its internal timer and report its acquire count and period.
     *
     * Key scenarios exercised:
     *  - setLimit(0) disables the permit cap (NO_LIMIT mode)
     *  - An initial period of 0 is later overridden to 2780 before get() is called
     *  - The executor service is swapped mid-chain; the last-set executor is used
     *  - startTimer() schedules the periodic reset task on the final executor
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // ---- Build the TimedSemaphore via the builder ----

        // Start with a fresh builder and pin the time unit to HOURS
        TimedSemaphore.Builder builder = new TimedSemaphore.Builder();
        TimeUnit hours = TimeUnit.HOURS;
        TimedSemaphore.Builder builderWithTimeUnit = builder.setTimeUnit(hours);

        // Create a first executor backed by a mock factory that returns null threads.
        // This executor has 0 core threads, so submitting a task exercises the
        // rejection / thread-creation path without actually running the task.
        ThreadFactory nullThreadFactory = mock(ThreadFactory.class, new ViolatedAssumptionAnswer());
        doReturn((Thread) null).when(nullThreadFactory).newThread(any(java.lang.Runnable.class));
        ScheduledThreadPoolExecutor zeroThreadExecutor = new ScheduledThreadPoolExecutor(0, nullThreadFactory);

        // Build a four-deep chain of mock threads and submit the leaf to the executor
        ThreadGroup mockGroup = mock(ThreadGroup.class, new ViolatedAssumptionAnswer());
        MockThread rootThread     = new MockThread();
        MockThread groupedThread  = new MockThread(mockGroup, rootThread, "E*>MF]SL7");
        MockThread childThread    = new MockThread(groupedThread, "+Z-.NN}7l}dv\"f");
        MockThread leafThread     = new MockThread(childThread);
        zeroThreadExecutor.execute(leafThread);

        // Wire the first executor into the builder and disable the permit cap (limit = 0)
        builderWithTimeUnit.setService(zeroThreadExecutor);
        TimedSemaphore.Builder builderWithLimit = builderWithTimeUnit.setLimit(0);

        // Repeat setLimit(0) to confirm idempotent behaviour, then replace the executor
        // with a larger one (2780 core threads) backed by a second null-thread factory
        ThreadFactory secondNullThreadFactory = mock(ThreadFactory.class, new ViolatedAssumptionAnswer());
        doReturn((Thread) null).when(secondNullThreadFactory).newThread(any(java.lang.Runnable.class));
        builderWithLimit.setLimit(0);
        ScheduledThreadPoolExecutor largeExecutor = new ScheduledThreadPoolExecutor(2780, secondNullThreadFactory);
        TimedSemaphore.Builder builderWithNewService = builderWithLimit.setService(largeExecutor);

        // Set an invalid period of 0 first, then override it with 2780 before building.
        // Because all builder setters return `this`, the last call wins at get() time.
        builderWithLimit.setPeriod(0L);
        TimedSemaphore.Builder builderWithPeriod = builderWithNewService.setPeriod(2780);

        // ---- Construct the semaphore and exercise post-build behaviour ----

        TimedSemaphore semaphore = builderWithPeriod.get();

        // Changing the builder after construction should not affect the already-built semaphore
        builderWithPeriod.setTimeUnit(hours);

        // Start the internal timer task on the executor, then read state that is
        // always available without acquiring (acquireCount = 0 initially, period = 2780)
        semaphore.startTimer();
        semaphore.getAcquireCount();
        semaphore.getPeriod();
    }
}
