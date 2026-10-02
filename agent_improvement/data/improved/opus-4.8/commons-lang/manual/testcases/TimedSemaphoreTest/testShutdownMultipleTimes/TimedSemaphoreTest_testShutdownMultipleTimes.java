package org.apache.commons.lang3.concurrent;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link TimedSemaphore#shutdown()} is idempotent: calling it
 * repeatedly must have the same effect as calling it once.
 */
public class TimedSemaphoreTest_testShutdownMultipleTimes extends AbstractLangTest {

    /** The length of the monitored time period. */
    private static final long PERIOD_MILLIS = 500;

    /** The time unit for {@link #PERIOD_MILLIS}. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** The number of permits allowed per period. */
    private static final int LIMIT = 10;

    /** The number of times shutdown() is invoked during the test. */
    private static final int SHUTDOWN_INVOCATIONS = 10;

    /**
     * A test-only {@link TimedSemaphore} that counts how often the internal
     * timer is started, so tests can assert on timer setup without relying on a
     * real executor.
     */
    private static class TimedSemaphoreTestImpl extends TimedSemaphore {

        /** Counts invocations of {@link #startTimer()}. */
        int startTimerCount;

        TimedSemaphoreTestImpl(final ScheduledExecutorService service, final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(service, timePeriod, timeUnit, limit);
        }

        @Override
        protected ScheduledFuture<?> startTimer() {
            startTimerCount++;
            return super.startTimer();
        }
    }

    /**
     * Records the expectation that the semaphore starts its periodic timer task,
     * returning the given future as the scheduled task handle.
     *
     * @param service the executor service mock
     * @param future  the future returned for the scheduled task
     */
    private void expectTimerStart(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Tests that invoking {@link TimedSemaphore#shutdown()} multiple times cancels
     * the timer task exactly once. Because the executor service is mocked,
     * EasyMock's verification asserts that {@code future.cancel(false)} is called
     * only on the first shutdown; any extra cancellation would fail verification.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testShutdownMultipleTimes() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);

        // The first acquire() starts the timer; the first shutdown() cancels it once.
        expectTimerStart(service, future);
        EasyMock.expect(Boolean.valueOf(future.cancel(false))).andReturn(Boolean.TRUE);
        EasyMock.replay(service, future);

        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.acquire();
        for (int i = 0; i < SHUTDOWN_INVOCATIONS; i++) {
            semaphore.shutdown();
        }

        EasyMock.verify(service, future);
    }
}
