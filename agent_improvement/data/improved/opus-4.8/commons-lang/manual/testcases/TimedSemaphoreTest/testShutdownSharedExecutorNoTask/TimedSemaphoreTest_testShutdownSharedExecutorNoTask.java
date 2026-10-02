package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testShutdownSharedExecutorNoTask extends AbstractLangTest {

    /** The length of the semaphore's time period. */
    private static final long PERIOD_MILLIS = 500;

    /** The time unit in which {@link #PERIOD_MILLIS} is expressed. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** The number of permits allowed per period. */
    private static final int LIMIT = 10;

    /**
     * Tests that calling {@code shutdown()} on a semaphore that uses a shared
     * (caller-provided) executor service and that has never started its timer
     * task does essentially nothing: the semaphore reports itself as shut down,
     * and the shared executor is left untouched (no methods are invoked on it).
     */
    @Test
    void testShutdownSharedExecutorNoTask() {
        // A strict mock with no expectations: any interaction would fail verify().
        final ScheduledExecutorService sharedExecutor =
                EasyMock.createMock(ScheduledExecutorService.class);
        EasyMock.replay(sharedExecutor);

        final TimedSemaphore semaphore =
                new TimedSemaphore(sharedExecutor, PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();

        assertTrue(semaphore.isShutdown(), "Semaphore should report being shut down");
        // The shared executor must not be touched, since no task was ever started.
        EasyMock.verify(sharedExecutor);
    }
}
