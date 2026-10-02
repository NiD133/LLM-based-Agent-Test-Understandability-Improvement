package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TimedSemaphore#getAvailablePermits()} correctly tracks
 * the number of non-blocking acquire calls remaining in the current period.
 */
public class TimedSemaphoreTest_testGetAvailablePermits extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;
    private static final int LIMIT = 10;

    /**
     * Configures the mock executor to expect the timer-start call that happens
     * on the first {@link TimedSemaphore#acquire()}.
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
     * Verifies that {@link TimedSemaphore#getAvailablePermits()} decrements with
     * each {@link TimedSemaphore#acquire()} call and resets to the full limit after
     * {@link TimedSemaphore#endOfPeriod()}.
     */
    @Test
    void testGetAvailablePermits() throws InterruptedException {
        // Set up a mock executor that records the periodic timer task scheduled on first acquire
        final ScheduledExecutorService mockExecutor = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> mockFuture = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(mockExecutor, mockFuture);
        EasyMock.replay(mockExecutor, mockFuture);

        final TimedSemaphore semaphore = new TimedSemaphore(mockExecutor, PERIOD_MILLIS, UNIT, LIMIT);

        // Each acquire should reduce the available permits by exactly one
        for (int acquired = 0; acquired < LIMIT; acquired++) {
            final int expectedAvailable = LIMIT - acquired;
            assertEquals(expectedAvailable, semaphore.getAvailablePermits(),
                    "Available permits should be " + expectedAvailable + " before acquire #" + (acquired + 1));
            semaphore.acquire();
        }

        // After the period ends, all permits should be restored to the full limit
        semaphore.endOfPeriod();
        assertEquals(LIMIT, semaphore.getAvailablePermits(),
                "Available permits should reset to the full limit after endOfPeriod()");

        EasyMock.verify(mockExecutor, mockFuture);
    }
}
