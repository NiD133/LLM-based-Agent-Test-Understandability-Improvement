package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testPassAfterShutdown extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;
    private static final int LIMIT = 10;

    /**
     * Verifies that calling acquire() after shutdown() throws IllegalStateException.
     */
    @Test
    void testPassAfterShutdown() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();
        assertThrows(IllegalStateException.class, semaphore::acquire);
    }
}
