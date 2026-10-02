package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testInitDefaultService extends AbstractLangTest {

    /** The monitored time period, expressed in {@link #TIME_UNIT}. */
    private static final long PERIOD_MILLIS = 500;

    /** The time unit for {@link #PERIOD_MILLIS}. */
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    /** The number of permits allowed per period. */
    private static final int LIMIT = 10;

    /**
     * Verifies that, when no executor service is supplied, the semaphore creates
     * its own {@link ScheduledThreadPoolExecutor} configured so that pending
     * periodic and delayed tasks are discarded on shutdown, and that this
     * executor is initially running.
     */
    @Test
    void testInitDefaultService() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, TIME_UNIT, LIMIT);

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
