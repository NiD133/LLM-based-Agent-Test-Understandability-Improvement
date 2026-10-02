package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testInitDefaultService extends AbstractLangTest {

    /** Time period used when constructing a semaphore with a default executor. */
    private static final long PERIOD_MILLIS = 500;

    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** Permit limit used when constructing the semaphore under test. */
    private static final int LIMIT = 10;

    /**
     * Verifies that when no executor service is supplied, TimedSemaphore creates
     * its own ScheduledThreadPoolExecutor and configures it so that:
     * <ul>
     *   <li>periodic tasks are <em>not</em> continued after shutdown</li>
     *   <li>delayed tasks are <em>not</em> executed after shutdown</li>
     *   <li>the executor is not already shut down at construction time</li>
     * </ul>
     */
    @Test
    void testInitDefaultService() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        final ScheduledThreadPoolExecutor exec = (ScheduledThreadPoolExecutor) semaphore.getExecutorService();

        assertFalse(exec.getContinueExistingPeriodicTasksAfterShutdownPolicy(),
                "Periodic tasks should not continue after shutdown");
        assertFalse(exec.getExecuteExistingDelayedTasksAfterShutdownPolicy(),
                "Delayed tasks should not execute after shutdown");
        assertFalse(exec.isShutdown(),
                "Executor should not be shut down immediately after construction");

        semaphore.shutdown();
    }
}
