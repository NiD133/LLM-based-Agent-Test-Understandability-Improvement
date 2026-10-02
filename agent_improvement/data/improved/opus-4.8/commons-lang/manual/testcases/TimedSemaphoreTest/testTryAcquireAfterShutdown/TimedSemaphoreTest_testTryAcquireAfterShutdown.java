package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testTryAcquireAfterShutdown extends AbstractLangTest {

    /** Length of the time frame monitored by the semaphore. */
    private static final long PERIOD_MILLIS = 500;

    /** Time unit for {@link #PERIOD_MILLIS}. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** Maximum number of permits allowed per period. */
    private static final int LIMIT = 10;

    /**
     * Once the semaphore has been shut down, tryAcquire() is no longer allowed
     * and must fail with an IllegalStateException.
     */
    @Test
    void testTryAcquireAfterShutdown() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);

        semaphore.shutdown();

        assertThrows(IllegalStateException.class, semaphore::tryAcquire);
    }
}
