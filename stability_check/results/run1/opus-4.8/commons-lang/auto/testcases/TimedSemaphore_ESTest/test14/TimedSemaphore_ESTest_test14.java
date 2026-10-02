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
     * When the limit is 0 the semaphore is switched off (NO_LIMIT), so every
     * acquire always succeeds and simply increments the acquire count without
     * ever blocking. After one tryAcquire() and one acquire() the acquire count
     * is 2, therefore the available permits are limit - acquireCount = 0 - 2 = -2.
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
