package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testShutdownOwnExecutor extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;
    private static final int LIMIT = 10;

    /**
     * When a TimedSemaphore is created without an external executor, it owns
     * its internal executor. Calling shutdown() must shut down both the semaphore
     * and that internally-owned executor service.
     */
    @Test
    void testShutdownOwnExecutor() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);

        semaphore.shutdown();

        assertTrue(semaphore.isShutdown(), "Not shutdown");
        assertTrue(semaphore.getExecutorService().isShutdown(), "Executor not shutdown");
    }
}
