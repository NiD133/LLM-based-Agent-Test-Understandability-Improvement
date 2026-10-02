package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testInitDefaultService extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;
    private static final int LIMIT = 10;

    /**
     * Verifies that when no executor service is provided, TimedSemaphore creates
     * a default ScheduledThreadPoolExecutor with safe shutdown policies and in a
     * non-shutdown state.
     */
    @Test
    void testInitDefaultService() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        final ScheduledThreadPoolExecutor defaultExecutor =
                (ScheduledThreadPoolExecutor) semaphore.getExecutorService();

        // The executor must not continue periodic tasks after shutdown is requested
        assertFalse(defaultExecutor.getContinueExistingPeriodicTasksAfterShutdownPolicy(),
                "Wrong periodic task policy");

        // The executor must not run delayed tasks that were queued before shutdown
        assertFalse(defaultExecutor.getExecuteExistingDelayedTasksAfterShutdownPolicy(),
                "Wrong delayed task policy");

        // The executor must be active (not shut down) upon creation
        assertFalse(defaultExecutor.isShutdown(), "Already shutdown");

        semaphore.shutdown();
    }
}
