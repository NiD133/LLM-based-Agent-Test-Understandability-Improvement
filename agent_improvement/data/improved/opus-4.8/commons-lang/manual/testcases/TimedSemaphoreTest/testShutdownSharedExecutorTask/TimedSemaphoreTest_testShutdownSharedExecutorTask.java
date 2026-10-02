package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link TimedSemaphore#shutdown()} behaves when the semaphore runs
 * on a <em>shared</em> (externally supplied) {@link ScheduledExecutorService} and
 * its periodic timer task has already been started.
 *
 * <p>Because the executor was not created by the semaphore, shutting the semaphore
 * down must <strong>not</strong> shut down the executor. Instead, the semaphore is
 * only expected to cancel its own periodic timer task.</p>
 */
public class TimedSemaphoreTest_testShutdownSharedExecutorTask extends AbstractLangTest {

    /**
     * A test-only {@link TimedSemaphore} subclass that records how often the timer
     * was started and how often a period ended, while otherwise delegating to the
     * real implementation.
     */
    private static class TimedSemaphoreTestImpl extends TimedSemaphore {

        /** Counts how many times the periodic timer task was started. */
        int startTimerCount;

        /** Counts how many monitored periods have ended. */
        int periodEnds;

        TimedSemaphoreTestImpl(final ScheduledExecutorService service, final long timePeriod,
                final TimeUnit timeUnit, final int limit) {
            super(service, timePeriod, timeUnit, limit);
        }

        @Override
        synchronized void endOfPeriod() {
            super.endOfPeriod();
            ++periodEnds;
        }

        @Override
        protected ScheduledFuture<?> startTimer() {
            ++startTimerCount;
            return super.startTimer();
        }
    }

    /** The length of the semaphore's monitored time period. */
    private static final long PERIOD_MILLIS = 500;

    /** The time unit that {@link #PERIOD_MILLIS} is expressed in. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** The number of permits allowed per period. */
    private static final int LIMIT = 10;

    /**
     * Records the expectation that the semaphore starts its periodic timer by
     * scheduling a task on the executor at the configured fixed rate, and that the
     * scheduling call returns the given future.
     *
     * @param service the mocked executor the semaphore schedules its timer on.
     * @param timerTask the future the executor is set up to return for that task.
     */
    private void expectTimerStarted(final ScheduledExecutorService service, final ScheduledFuture<?> timerTask) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(),
                EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(timerTask);
    }

    /**
     * Tests {@link TimedSemaphore#shutdown()} for a shared executor after the timer
     * task has been started. In this case the timer task must be canceled while the
     * shared executor itself is left untouched.
     *
     * @throws InterruptedException so we don't have to catch it.
     */
    @Test
    void testShutdownSharedExecutorTask() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> timerTask = EasyMock.createMock(ScheduledFuture.class);

        // The first acquire() starts the timer; shutdown() then cancels that task.
        expectTimerStarted(service, timerTask);
        EasyMock.expect(Boolean.valueOf(timerTask.cancel(false))).andReturn(Boolean.TRUE);
        EasyMock.replay(service, timerTask);

        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.acquire();
        semaphore.shutdown();

        assertTrue(semaphore.isShutdown(), "Semaphore should be marked as shut down");
        // Verifies the timer task was canceled and the shared executor was never shut down.
        EasyMock.verify(service, timerTask);
    }
}
