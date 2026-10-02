package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TimedSemaphore#acquire()} correctly serializes concurrent
 * threads when the semaphore limit is 1.  With limit=1, only one thread per
 * time period may pass; the others must block until {@code endOfPeriod()} is
 * called.
 */
public class TimedSemaphoreTest_testAcquireMultipleThreads extends AbstractLangTest {

    // -----------------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------------

    /** Duration of one semaphore period in milliseconds. */
    private static final long PERIOD_MILLIS = 500;

    /** Time unit that matches {@link #PERIOD_MILLIS}. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** Number of worker threads (and therefore periods) exercised by the test. */
    private static final int THREAD_COUNT = 10;

    /** The permit limit per period – only one thread may pass at a time. */
    private static final int LIMIT_ONE = 1;

    // -----------------------------------------------------------------------
    // Helper: TimedSemaphoreTestImpl
    // -----------------------------------------------------------------------

    /**
     * A {@link TimedSemaphore} subclass that counts down a configurable
     * {@link CountDownLatch} after every successful {@link #acquire()}.  This
     * lets the test thread know exactly when a worker thread has passed the
     * semaphore without relying on timing.
     */
    private static final class TimedSemaphoreTestImpl extends TimedSemaphore {

        /**
         * Latch decremented once per successful {@code acquire()}.  Replaced
         * between periods by the test thread.
         */
        volatile CountDownLatch latch;

        TimedSemaphoreTestImpl(final ScheduledExecutorService service,
                final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(service, timePeriod, timeUnit, limit);
        }

        /** Delegates to super, then signals the current latch. */
        @Override
        public synchronized void acquire() throws InterruptedException {
            super.acquire();
            if (latch != null) {
                latch.countDown();
            }
        }
    }

    // -----------------------------------------------------------------------
    // Helper: SemaphoreThread
    // -----------------------------------------------------------------------

    /**
     * A worker thread that calls {@link TimedSemaphore#acquire()} a fixed
     * number of times.  When {@code latchCount > 0} it also counts down the
     * supplied latch after the first {@code latchCount} acquisitions, giving
     * the test thread a synchronisation point.
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

    // -----------------------------------------------------------------------
    // Test method
    // -----------------------------------------------------------------------

    /**
     * Verifies that with {@code limit=1}, exactly one thread passes the
     * semaphore per period.
     *
     * <p>Scenario:
     * <ol>
     *   <li>Start {@value #THREAD_COUNT} threads, each calling {@code acquire()}
     *       once.</li>
     *   <li>For each period, wait until exactly one thread has acquired the
     *       semaphore, assert the acquire count is 1, then manually advance to
     *       the next period via {@code endOfPeriod()}.</li>
     *   <li>After all periods, join all threads and verify the mock executor
     *       was used exactly as expected (timer started once).</li>
     * </ol>
     */
    @Test
    void testAcquireMultipleThreads() throws InterruptedException {
        // --- Set up a mock executor so the timer task never fires on its own ---
        final ScheduledExecutorService mockExecutor = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> mockFuture = EasyMock.createMock(ScheduledFuture.class);
        mockExecutor.scheduleAtFixedRate(
                (Runnable) EasyMock.anyObject(),
                EasyMock.eq(PERIOD_MILLIS),
                EasyMock.eq(PERIOD_MILLIS),
                EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(mockFuture);
        EasyMock.replay(mockExecutor, mockFuture);

        // --- Create the semaphore with a single-permit limit ---
        final TimedSemaphoreTestImpl semaphore =
                new TimedSemaphoreTestImpl(mockExecutor, PERIOD_MILLIS, UNIT, LIMIT_ONE);

        // Arm the first latch: fires when one thread successfully acquires.
        semaphore.latch = new CountDownLatch(LIMIT_ONE);

        // --- Start all worker threads; each will block until its turn ---
        final SemaphoreThread[] workers = new SemaphoreThread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            // Each thread acquires once and uses no external latch (it relies
            // on the semaphore's own latch mechanism via TimedSemaphoreTestImpl).
            workers[i] = new SemaphoreThread(semaphore, null, 1, 0);
            workers[i].start();
        }

        // --- Drive the semaphore through THREAD_COUNT periods manually ---
        for (int period = 0; period < THREAD_COUNT; period++) {
            // Wait for exactly one thread to pass the semaphore in this period.
            semaphore.latch.await();

            assertEquals(1, semaphore.getAcquireCount(),
                    "Exactly one thread should hold a permit within one period");

            // Prepare the latch for the next period before advancing, so no
            // acquire can slip through unnoticed.
            semaphore.latch = new CountDownLatch(LIMIT_ONE);

            // Advance to the next period: resets the counter and wakes blocked threads.
            semaphore.endOfPeriod();

            assertEquals(1, semaphore.getLastAcquiresPerPeriod(),
                    "Exactly one acquire should have been recorded for the completed period");
        }

        // --- Wait for all worker threads to finish ---
        for (final SemaphoreThread worker : workers) {
            worker.join();
        }

        // --- Verify the mock was exercised exactly as expected ---
        EasyMock.verify(mockExecutor, mockFuture);
    }
}
