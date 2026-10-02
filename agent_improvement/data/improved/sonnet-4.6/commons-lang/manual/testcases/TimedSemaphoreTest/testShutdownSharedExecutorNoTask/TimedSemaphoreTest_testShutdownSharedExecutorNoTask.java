package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testShutdownSharedExecutorNoTask extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;
    private static final int LIMIT = 10;

    /**
     * Tests that shutting down a semaphore backed by a shared (externally-owned)
     * executor, before any acquire() call starts the timer task, merely marks the
     * semaphore as shut down without touching the shared executor.
     *
     * The mock is replayed with no expectations, so any call on the executor
     * service during shutdown() would cause a verification failure.
     */
    @Test
    void testShutdownSharedExecutorNoTask() {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        EasyMock.replay(service);

        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();

        assertTrue(semaphore.isShutdown(), "Semaphore should be marked as shut down");
        EasyMock.verify(service); // confirms the shared executor was never touched
    }
}
