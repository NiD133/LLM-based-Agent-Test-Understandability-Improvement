package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link TimedSemaphore#getAvailablePermits()}.
 */
public class TimedSemaphoreTest_testGetAvailablePermits extends AbstractLangTest {

    /** The length of one time period the semaphore monitors. */
    private static final long PERIOD_MILLIS = 500;

    /** The time unit in which the period is expressed. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** The number of permits available per period. */
    private static final int LIMIT = 10;

    /**
     * Configures the executor-service mock to expect that the semaphore schedules
     * its periodic timer task. The mock returns the supplied future for that task.
     *
     * @param service the executor-service mock.
     * @param future  the future the scheduled task is represented by.
     */
    private void expectTimerToBeScheduled(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Tests that {@link TimedSemaphore#getAvailablePermits()} reports the number of
     * permits still free in the current period, and that it is reset to the full
     * limit once a new period begins.
     *
     * @throws InterruptedException so we don't have to catch it.
     */
    @Test
    void testGetAvailablePermits() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        expectTimerToBeScheduled(service, future);
        EasyMock.replay(service, future);

        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);

        // Each acquire() consumes one permit, so the available count should
        // decrease by one on every iteration: LIMIT, LIMIT - 1, ... down to 1.
        for (int acquired = 0; acquired < LIMIT; acquired++) {
            assertEquals(LIMIT - acquired, semaphore.getAvailablePermits(), "Wrong available count at " + acquired);
            semaphore.acquire();
        }

        // Ending the period restores all permits for the next period.
        semaphore.endOfPeriod();
        assertEquals(LIMIT, semaphore.getAvailablePermits(), "Wrong available count in new period");

        EasyMock.verify(service, future);
    }
}
