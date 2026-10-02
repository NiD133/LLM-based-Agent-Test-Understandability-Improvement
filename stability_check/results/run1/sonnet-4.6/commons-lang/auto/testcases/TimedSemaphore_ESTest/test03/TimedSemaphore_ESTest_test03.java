package org.apache.commons.lang3.concurrent;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TimedSemaphore_ESTest_test03 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Create a semaphore with a 1228-hour period and a limit of 8 permits
        TimedSemaphore semaphore = new TimedSemaphore(1228L, TimeUnit.HOURS, 8);

        // Simulate end of period, start the timer, acquire a permit, then shut down
        semaphore.endOfPeriod();
        semaphore.startTimer();
        semaphore.tryAcquire();
        semaphore.shutdown();
        // Calling shutdown a second time should be a no-op
        semaphore.shutdown();

        // Verify that the builder factory method is accessible
        TimedSemaphore.builder();

        // Creating a semaphore with a negative period must throw IllegalArgumentException
        try {
            new TimedSemaphore((ScheduledExecutorService) null, (-1229L), TimeUnit.NANOSECONDS, (-745));
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Time period must be greater than 0.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
