package org.apache.commons.lang3.concurrent;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TimedSemaphore_ESTest_test14 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * When the limit is set to 0 the semaphore is effectively switched off: both
     * {@code tryAcquire()} and {@code acquire()} pass through without blocking,
     * yet each call still increments the internal acquire counter.
     *
     * <p>With a limit of 0 and two successful acquisitions, the number of available
     * permits ({@code limit - acquireCount}) becomes {@code 0 - 2 == -2}.</p>
     */
    @Test(timeout = 4000)
    public void availablePermitsGoNegativeWhenLimitIsDisabled() throws Throwable {
        final int disabledLimit = 0;
        TimedSemaphore semaphore = new TimedSemaphore(1091L, TimeUnit.HOURS, disabledLimit);

        semaphore.tryAcquire();
        semaphore.acquire();

        assertEquals(-2, semaphore.getAvailablePermits());
    }
}
