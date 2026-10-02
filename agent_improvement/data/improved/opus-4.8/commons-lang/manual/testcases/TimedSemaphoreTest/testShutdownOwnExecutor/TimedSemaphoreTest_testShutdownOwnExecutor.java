package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link TimedSemaphore#shutdown()} when the semaphore owns its executor.
 */
public class TimedSemaphoreTest_testShutdownOwnExecutor extends AbstractLangTest {

    /** Length of the monitored time period. */
    private static final long PERIOD_MILLIS = 500;

    /** Time unit for {@link #PERIOD_MILLIS}. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** Number of permits allowed per period. */
    private static final int LIMIT = 10;

    /**
     * When the semaphore creates its own executor service, calling {@code shutdown()}
     * must shut down both the semaphore and that internally created executor.
     */
    @Test
    void testShutdownOwnExecutor() {
        // The four-arg-free constructor makes the semaphore create (and therefore own)
        // its executor service.
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);

        semaphore.shutdown();

        assertTrue(semaphore.isShutdown(), "Semaphore should be shut down");
        assertTrue(semaphore.getExecutorService().isShutdown(), "Owned executor should be shut down");
    }
}
