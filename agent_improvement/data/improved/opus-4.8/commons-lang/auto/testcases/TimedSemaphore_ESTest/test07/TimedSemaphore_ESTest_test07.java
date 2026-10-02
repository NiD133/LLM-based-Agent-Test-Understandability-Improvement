package org.apache.commons.lang3.concurrent;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TimedSemaphore_ESTest_test07 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Exercises the basic lifecycle methods of a TimedSemaphore (end of period,
     * shutdown, and the accessor methods) on an instance built with a caller
     * supplied executor, then verifies that constructing a semaphore with a
     * non-positive time period is rejected with an IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Build a semaphore around a caller-provided executor service, using a
        // period of 4507 minutes and a (negative) limit of -2399.
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4507);
        TimedSemaphore semaphore = new TimedSemaphore(executor, 4507, TimeUnit.MINUTES, -2399);

        // Lifecycle calls should all succeed without throwing.
        semaphore.endOfPeriod();
        semaphore.shutdown();
        semaphore.getAcquireCount();
        // shutdown() is idempotent: a second call has no effect.
        semaphore.shutdown();
        // Reading back the limit must not throw.
        semaphore.getLimit();

        // Creating a Builder directly is allowed and has no side effects here.
        new TimedSemaphore.Builder();

        // A time period of -1 is invalid and must be rejected by Validate.
        try {
            new TimedSemaphore(-1, TimeUnit.MINUTES, 1);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Time period must be greater than 0.
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
