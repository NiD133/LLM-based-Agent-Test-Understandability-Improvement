package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link TimedSemaphore} correctly rolls over from one time period
 * to the next when it is hammered with many {@code acquire()} calls.
 */
public class TimedSemaphoreTest_testAcquireMultiplePeriods extends AbstractLangTest {

    /** A very short monitored period (in milliseconds) so that periods end quickly. */
    private static final long PERIOD_MILLIS = 50;

    /** The total number of {@code acquire()} calls performed by the worker thread. */
    private static final int ACQUIRE_COUNT = 1000;

    /** The permit limit per period; chosen so several periods are needed to serve all calls. */
    private static final int LIMIT_PER_PERIOD = ACQUIRE_COUNT / 4;

    /**
     * Tests a large number of {@code acquire()} invocations that span multiple
     * periods. The period is deliberately very short while a background thread
     * calls the semaphore many times, so at least one period must end before the
     * thread finishes. Afterwards the recorded number of period ends must be
     * greater than zero.
     */
    @Test
    void testAcquireMultiplePeriods() throws InterruptedException {
        final TimedSemaphoreTestImpl semaphore =
                new TimedSemaphoreTestImpl(PERIOD_MILLIS, TimeUnit.MILLISECONDS, 1);
        semaphore.setLimit(LIMIT_PER_PERIOD);

        // The latch lets the main thread wait until every acquire() has happened.
        final CountDownLatch latch = new CountDownLatch(ACQUIRE_COUNT);
        final SemaphoreThread worker =
                new SemaphoreThread(semaphore, latch, ACQUIRE_COUNT, ACQUIRE_COUNT);

        worker.start();
        latch.await();
        semaphore.shutdown();

        assertTrue(semaphore.getPeriodEnds() > 0, "End of period not reached");
    }

    /**
     * A {@link TimedSemaphore} subclass that records how often a period has ended,
     * which is what this test asserts on.
     */
    private static final class TimedSemaphoreTestImpl extends TimedSemaphore {

        /** Counts the number of invocations of {@link #endOfPeriod()}. */
        private int periodEnds;

        TimedSemaphoreTestImpl(final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(timePeriod, timeUnit, limit);
        }

        @Override
        synchronized void endOfPeriod() {
            super.endOfPeriod();
            periodEnds++;
        }

        /**
         * @return how many times a monitored period has ended so far.
         */
        synchronized int getPeriodEnds() {
            return periodEnds;
        }
    }

    /**
     * A background thread that repeatedly acquires permits from the semaphore and
     * counts the latch down for each of the first {@code latchCount} acquisitions.
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
            } catch (final InterruptedException iex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
