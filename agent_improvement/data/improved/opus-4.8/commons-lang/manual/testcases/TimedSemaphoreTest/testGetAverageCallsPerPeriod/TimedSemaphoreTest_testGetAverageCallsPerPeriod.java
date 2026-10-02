package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link TimedSemaphore#getAverageCallsPerPeriod()}.
 */
public class TimedSemaphoreTest_testGetAverageCallsPerPeriod extends AbstractLangTest {

    /** The length of one monitored time period. */
    private static final long PERIOD_MILLIS = 500;

    /** The time unit in which {@link #PERIOD_MILLIS} is expressed. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** The maximum number of acquire() calls allowed per period. */
    private static final int LIMIT = 10;

    /** Tolerance used when comparing the (floating-point) average. */
    private static final double TOLERANCE = .005;

    /**
     * Configures the mocked executor service so that, when the semaphore starts
     * its internal timer, the periodic {@code scheduleAtFixedRate} call is
     * expected and returns the supplied future.
     *
     * @param service the mocked executor service
     * @param future  the future the scheduling call should return
     */
    private void expectTimerStart(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate(EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Verifies that {@code getAverageCallsPerPeriod()} returns the running mean
     * of successful acquire() calls across all completed periods.
     */
    @Test
    void testGetAverageCallsPerPeriod() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        expectTimerStart(service, future);
        EasyMock.replay(service, future);

        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);

        // Period 1: a single acquire -> average is 1 call / 1 period = 1.0
        semaphore.acquire();
        semaphore.endOfPeriod();
        assertEquals(1.0, semaphore.getAverageCallsPerPeriod(), TOLERANCE, "Wrong average (1)");

        // Period 2: two more acquires -> average is 3 calls / 2 periods = 1.5
        semaphore.acquire();
        semaphore.acquire();
        semaphore.endOfPeriod();
        assertEquals(1.5, semaphore.getAverageCallsPerPeriod(), TOLERANCE, "Wrong average (2)");

        EasyMock.verify(service, future);
    }
}
