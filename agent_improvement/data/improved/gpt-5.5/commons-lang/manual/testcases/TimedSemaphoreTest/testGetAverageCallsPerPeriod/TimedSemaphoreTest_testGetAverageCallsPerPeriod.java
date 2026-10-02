package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testGetAverageCallsPerPeriod extends AbstractLangTest {

    /**
     * Constant for the time period.
     */
    private static final long PERIOD_MILLIS = 500;

    /**
     * Constant for the time unit.
     */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /**
     * Constant for the default limit.
     */
    private static final int LIMIT = 10;

    /**
     * Prepares an executor service mock to expect the start of the timer.
     *
     * @param service the mock
     * @param future the future
     */
    private void prepareStartTimer(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Tests the methods for statistics.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testGetAverageCallsPerPeriod() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);

        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);

        semaphore.acquire();
        semaphore.endOfPeriod();
        assertEquals(1.0, semaphore.getAverageCallsPerPeriod(), .005, "Wrong average (1)");

        semaphore.acquire();
        semaphore.acquire();
        semaphore.endOfPeriod();
        assertEquals(1.5, semaphore.getAverageCallsPerPeriod(), .005, "Wrong average (2)");

        EasyMock.verify(service, future);
    }
}
