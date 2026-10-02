package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TimedSemaphore} creates a suitable default executor service
 * when none is supplied at construction time.
 */
public class TimedSemaphoreTest_testInitDefaultService extends AbstractLangTest {

    /** The time period used when constructing the semaphore. */
    private static final long PERIOD_MILLIS = 500;

    /** The time unit that goes with {@link #PERIOD_MILLIS}. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** The permit limit used when constructing the semaphore. */
    private static final int LIMIT = 10;

    /**
     * When no executor service is provided, the semaphore must create its own
     * {@link ScheduledThreadPoolExecutor}. That default executor should not
     * keep running periodic or delayed tasks after a shutdown, and it should be
     * active (not yet shut down) right after construction.
     */
    @Test
    void testInitDefaultService() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);

        final ScheduledThreadPoolExecutor defaultExecutor =
                (ScheduledThreadPoolExecutor) semaphore.getExecutorService();

        assertFalse(defaultExecutor.getContinueExistingPeriodicTasksAfterShutdownPolicy(),
                "Wrong periodic task policy");
        assertFalse(defaultExecutor.getExecuteExistingDelayedTasksAfterShutdownPolicy(),
                "Wrong delayed task policy");
        assertFalse(defaultExecutor.isShutdown(), "Already shutdown");

        semaphore.shutdown();
    }
}
