package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testShutdownSharedExecutorTask extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;

    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    private static final int LIMIT = 10;

    private static final class TimedSemaphoreTestImpl extends TimedSemaphore {
        TimedSemaphoreTestImpl(final ScheduledExecutorService service, final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(service, timePeriod, timeUnit, limit);
        }
    }

    /**
     * Expects the first acquire() call to start the periodic timer task.
     *
     * @param executor the scheduler supplied to the semaphore
     * @param scheduledTask the future returned for the scheduled timer task
     */
    private void expectTimerStart(final ScheduledExecutorService executor, final ScheduledFuture<?> scheduledTask) {
        executor.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(scheduledTask);
    }

    /**
     * Tests the shutdown() method for a shared executor after the task was
     * started. In this case the task must be canceled.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testShutdownSharedExecutorTask() throws InterruptedException {
        final ScheduledExecutorService executor = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> scheduledTask = EasyMock.createMock(ScheduledFuture.class);

        expectTimerStart(executor, scheduledTask);
        EasyMock.expect(Boolean.valueOf(scheduledTask.cancel(false))).andReturn(Boolean.TRUE);
        EasyMock.replay(executor, scheduledTask);

        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(executor, PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.acquire();
        semaphore.shutdown();

        assertTrue(semaphore.isShutdown(), "Not shutdown");
        EasyMock.verify(executor, scheduledTask);
    }
}
