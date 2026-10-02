package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testGetAverageCallsPerPeriod extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;
    private static final int LIMIT = 10;

    /** Acceptable floating-point delta for average comparisons. */
    private static final double AVERAGE_DELTA = 0.005;

    /**
     * Configures the mock executor to expect a single scheduleAtFixedRate call,
     * which TimedSemaphore issues when the first acquire() starts the internal timer.
     */
    private void prepareStartTimer(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate(
                (Runnable) EasyMock.anyObject(),
                EasyMock.eq(PERIOD_MILLIS),
                EasyMock.eq(PERIOD_MILLIS),
                EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Verifies that getAverageCallsPerPeriod() returns the correct running average
     * across multiple time periods.
     *
     * <p>Period 1: 1 acquire → average = 1.0
     * <p>Period 2: 2 acquires → cumulative average = (1 + 2) / 2 = 1.5
     */
    @Test
    void testGetAverageCallsPerPeriod() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);

        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);

        // Period 1: one acquire, then signal end-of-period → average = 1/1 = 1.0
        semaphore.acquire();
        semaphore.endOfPeriod();
        assertEquals(1.0, semaphore.getAverageCallsPerPeriod(), AVERAGE_DELTA,
                "After period 1 (1 acquire), average should be 1.0");

        // Period 2: two acquires, then signal end-of-period → average = (1+2)/2 = 1.5
        semaphore.acquire();
        semaphore.acquire();
        semaphore.endOfPeriod();
        assertEquals(1.5, semaphore.getAverageCallsPerPeriod(), AVERAGE_DELTA,
                "After period 2 (2 acquires), cumulative average should be 1.5");

        EasyMock.verify(service, future);
    }
}
