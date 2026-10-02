package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testAcquireMultiplePeriods extends AbstractLangTest {

    /** Period length in milliseconds used during the multi-period stress test. */
    private static final long SHORT_PERIOD_MILLIS = 50;

    /**
     * Thread that repeatedly calls {@link TimedSemaphore#acquire()} and counts
     * down a latch after each successful acquisition (up to {@code latchCount} times).
     */
    private static final class SemaphoreThread extends Thread {

        private final TimedSemaphore semaphore;
        private final CountDownLatch latch;
        private final int acquireCount;
        private final int latchCount;

        SemaphoreThread(final TimedSemaphore semaphore, final CountDownLatch latch,
                final int acquireCount, final int latchCount) {
            this.semaphore = semaphore;
            this.latch = latch;
            this.acquireCount = acquireCount;
            this.latchCount = latchCount;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < acquireCount; i++) {
                    semaphore.acquire();
                    if (i < latchCount) {
                        latch.countDown();
                    }
                }
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Subclass of {@link TimedSemaphore} that counts how many times
     * {@link #endOfPeriod()} has been invoked, enabling tests to verify that
     * at least one period boundary was crossed.
     */
    private static final class TimedSemaphoreTestImpl extends TimedSemaphore {

        /** Tracks the number of completed periods. */
        private int periodEnds;

        /** Optional future returned instead of starting a real timer (unused here). */
        ScheduledFuture<?> schedFuture;

        TimedSemaphoreTestImpl(final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(timePeriod, timeUnit, limit);
        }

        TimedSemaphoreTestImpl(final ScheduledExecutorService service, final long timePeriod,
                final TimeUnit timeUnit, final int limit) {
            super(service, timePeriod, timeUnit, limit);
        }

        @Override
        protected synchronized void endOfPeriod() {
            super.endOfPeriod();
            periodEnds++;
        }

        synchronized int getPeriodEnds() {
            return periodEnds;
        }

        @Override
        protected ScheduledFuture<?> startTimer() {
            return schedFuture != null ? schedFuture : super.startTimer();
        }
    }

    /**
     * Tests that a large number of {@link TimedSemaphore#acquire()} calls issued
     * from a background thread will span multiple time periods.
     *
     * <p>The semaphore is configured with a very short period and a limit that is
     * much smaller than the total number of acquisitions, so the background thread
     * is forced to wait for several period boundaries. The test succeeds when at
     * least one period end has been observed, confirming the rate-limiting logic
     * cycles correctly across periods.</p>
     */
    @Test
    void testAcquireMultiplePeriods() throws InterruptedException {
        final int totalAcquires = 1000;
        final int acquiresPerPeriod = totalAcquires / 4;

        final TimedSemaphoreTestImpl semaphore =
                new TimedSemaphoreTestImpl(SHORT_PERIOD_MILLIS, TimeUnit.MILLISECONDS, 1);
        semaphore.setLimit(acquiresPerPeriod);

        final CountDownLatch allAcquiresDone = new CountDownLatch(totalAcquires);
        final SemaphoreThread acquirerThread =
                new SemaphoreThread(semaphore, allAcquiresDone, totalAcquires, totalAcquires);

        acquirerThread.start();
        allAcquiresDone.await();
        semaphore.shutdown();

        assertTrue(semaphore.getPeriodEnds() > 0, "End of period not reached");
    }
}
