package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testShutdownSharedExecutorNoTask extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final TimeUnit PERIOD_UNIT = TimeUnit.MILLISECONDS;
    private static final int LIMIT = 10;

    /**
     * Tests the shutdown() method for a shared executor service before a task
     * was started. This should do pretty much nothing.
     */
    @Test
    @SuppressWarnings("deprecation")
    void testShutdownSharedExecutorNoTask() {
        final ScheduledExecutorService sharedExecutor = EasyMock.createMock(ScheduledExecutorService.class);
        EasyMock.replay(sharedExecutor);

        final TimedSemaphore semaphore = new TimedSemaphore(sharedExecutor, PERIOD_MILLIS, PERIOD_UNIT, LIMIT);
        semaphore.shutdown();

        assertTrue(semaphore.isShutdown(), "Not shutdown");
        EasyMock.verify(sharedExecutor);
    }
}
