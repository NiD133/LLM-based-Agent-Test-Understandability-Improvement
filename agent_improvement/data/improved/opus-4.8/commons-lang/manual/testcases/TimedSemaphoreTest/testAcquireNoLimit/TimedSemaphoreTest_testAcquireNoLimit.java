package org.apache.commons.lang3.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link TimedSemaphore#acquire()} when the semaphore is configured with
 * {@link TimedSemaphore#NO_LIMIT}.
 *
 * <p>With no limit set, {@code acquire()} must never block, no matter how many
 * times it is called within a single period. This test verifies that by letting
 * a worker thread call {@code acquire()} far more times than any sensible limit
 * and asserting (via a {@link CountDownLatch}) that every call returns.</p>
 *
 * <p>The {@link ScheduledExecutorService} is mocked so that the periodic timer
 * is never actually started in the background; we only verify that the semaphore
 * schedules its monitoring task exactly once, with the configured period and
 * time unit.</p>
 */
public class TimedSemaphoreTest_testAcquireNoLimit extends AbstractLangTest {

    /** The length of the semaphore's monitored time period. */
    private static final long PERIOD_MILLIS = 500;

    /** The time unit matching {@link #PERIOD_MILLIS}. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** How many times the worker thread calls {@code acquire()}. */
    private static final int ACQUIRE_CALLS = 1000;

    /**
     * Configures the mock executor service to expect exactly one call to
     * {@code scheduleAtFixedRate(...)} (the semaphore's internal timer start)
     * and to return the supplied future.
     *
     * @param service the mock executor service
     * @param timerTask the future the mock should return for the scheduled task
     */
    private void expectTimerStart(final ScheduledExecutorService service, final ScheduledFuture<?> timerTask) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(),
                EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(timerTask);
    }

    /**
     * With no limit configured, calling {@code acquire()} 1000 times must never
     * block, even though the time period never elapses (the scheduler is mocked).
     */
    @Test
    void testAcquireNoLimit() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> timerTask = EasyMock.createMock(ScheduledFuture.class);
        expectTimerStart(service, timerTask);
        EasyMock.replay(service, timerTask);

        final TimedSemaphoreTestImpl semaphore =
                new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, TimedSemaphore.NO_LIMIT);

        // The latch reaches zero only once every acquire() call has returned.
        final CountDownLatch allAcquiresDone = new CountDownLatch(ACQUIRE_CALLS);
        final SemaphoreThread worker = new SemaphoreThread(semaphore, allAcquiresDone, ACQUIRE_CALLS, ACQUIRE_CALLS);
        worker.start();

        // Blocks until the worker has completed all acquire() calls; if any call
        // had blocked, this test would hang rather than complete.
        allAcquiresDone.await();

        EasyMock.verify(service, timerTask);
    }

    /**
     * A {@link TimedSemaphore} subclass used as a test double. It records how many
     * times the monitored period ended. In this test the scheduler is mocked, so
     * {@link #endOfPeriod()} is never actually invoked.
     */
    private static final class TimedSemaphoreTestImpl extends TimedSemaphore {

        /** Counts how many times {@link #endOfPeriod()} was invoked. */
        private int periodEnds;

        TimedSemaphoreTestImpl(final ScheduledExecutorService service, final long timePeriod,
                final TimeUnit timeUnit, final int limit) {
            super(service, timePeriod, timeUnit, limit);
        }

        @Override
        protected synchronized void endOfPeriod() {
            super.endOfPeriod();
            periodEnds++;
        }
    }

    /**
     * A worker thread that calls {@code acquire()} on a semaphore a fixed number
     * of times. For each of the first {@code latchCount} calls it also counts the
     * latch down, allowing the main thread to wait for completion.
     */
    private static final class SemaphoreThread extends Thread {

        private final TimedSemaphore semaphore;
        private final CountDownLatch latch;
        private final int acquireCalls;
        private final int latchCount;

        SemaphoreThread(final TimedSemaphore semaphore, final CountDownLatch latch,
                final int acquireCalls, final int latchCount) {
            this.semaphore = semaphore;
            this.latch = latch;
            this.acquireCalls = acquireCalls;
            this.latchCount = latchCount;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < acquireCalls; i++) {
                    semaphore.acquire();
                    if (i < latchCount) {
                        latch.countDown();
                    }
                }
            } catch (final InterruptedException iex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
