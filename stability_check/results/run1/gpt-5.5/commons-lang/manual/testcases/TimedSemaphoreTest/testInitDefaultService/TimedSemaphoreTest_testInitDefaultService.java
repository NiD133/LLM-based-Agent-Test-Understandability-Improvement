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
     * Tests whether a default executor service is created if no service is
     * provided.
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
