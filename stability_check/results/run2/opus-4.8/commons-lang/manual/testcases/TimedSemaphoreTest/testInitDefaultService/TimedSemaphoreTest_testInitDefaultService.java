package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link TimedSemaphore} constructed without an explicit
 * executor service creates and configures a suitable default one.
 */
public class TimedSemaphoreTest_testInitDefaultService extends AbstractLangTest {

    /** Length of the time period the semaphore monitors. */
    private static final long PERIOD_MILLIS = 500;

    /** Time unit that applies to {@link #PERIOD_MILLIS}. */
    private static final TimeUnit PERIOD_UNIT = TimeUnit.MILLISECONDS;

    /** Number of permits granted per period. */
    private static final int LIMIT = 10;

    /**
     * When no executor service is supplied, the semaphore should build its own
     * {@link ScheduledThreadPoolExecutor} that is running and configured to
     * discard any outstanding tasks once it is shut down.
     */
    @Test
    void testInitDefaultService() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, PERIOD_UNIT, LIMIT);

        final ScheduledThreadPoolExecutor defaultExecutor =
                (ScheduledThreadPoolExecutor) semaphore.getExecutorService();

        assertFalse(defaultExecutor.getContinueExistingPeriodicTasksAfterShutdownPolicy(),
                "Wrong periodic task policy");
        assertFalse(defaultExecutor.getExecuteExistingDelayedTasksAfterShutdownPolicy(),
                "Wrong delayed task policy");
        assertFalse(defaultExecutor.isShutdown(),
                "Already shutdown");

        semaphore.shutdown();
    }
}
