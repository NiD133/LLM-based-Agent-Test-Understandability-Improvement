package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testPassAfterShutdown extends AbstractLangTest {

    /** The length of the time frame monitored by the semaphore. */
    private static final long PERIOD_MILLIS = 500;

    /** The time unit applied to {@link #PERIOD_MILLIS}. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** The number of permits allowed per period. */
    private static final int LIMIT = 10;

    /**
     * Verifies that calling acquire() after the semaphore has been shut down is
     * rejected with an IllegalStateException, since a shut-down semaphore must
     * no longer be used.
     */
    @Test
    void testPassAfterShutdown() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);

        semaphore.shutdown();

        assertThrows(IllegalStateException.class, semaphore::acquire);
    }
}
