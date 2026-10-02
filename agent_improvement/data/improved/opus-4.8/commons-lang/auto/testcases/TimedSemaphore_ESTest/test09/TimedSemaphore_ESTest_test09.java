package org.apache.commons.lang3.concurrent;

import static org.junit.Assert.*;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TimedSemaphore_ESTest_test09 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Exercises a {@link TimedSemaphore} that was created with a negative limit and a {@code null}
     * executor service, together with its {@link TimedSemaphore.Builder}. With a non-positive limit
     * the semaphore is effectively switched off, so both {@code tryAcquire()} and {@code acquire()}
     * are allowed to pass without blocking, and the various counters can be queried safely.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        final TimeUnit timeUnit = TimeUnit.HOURS;

        // A semaphore with no real executor and a negative (i.e. "no") limit.
        final TimedSemaphore semaphore = new TimedSemaphore((ScheduledExecutorService) null, 2280L, timeUnit, -3414);

        // Configure a builder independently of the semaphore above.
        final TimedSemaphore.Builder builder = TimedSemaphore.builder();
        TimedSemaphore.builder();

        // Drive the semaphore through its lifecycle methods.
        semaphore.startTimer();
        semaphore.setLimit(-3414);
        semaphore.endOfPeriod();

        // setTimeUnit returns the same builder instance.
        final TimedSemaphore.Builder sameBuilder = builder.setTimeUnit(timeUnit);
        builder.setPeriod(0);

        // With a non-positive limit, acquiring never blocks.
        semaphore.tryAcquire();
        semaphore.acquire();

        semaphore.getLastAcquiresPerPeriod();
        builder.setService((ScheduledExecutorService) null);
        semaphore.getAcquireCount();
        semaphore.getAcquireCount();

        TimedSemaphore.builder();
        sameBuilder.setPeriod(3853L);
    }
}
