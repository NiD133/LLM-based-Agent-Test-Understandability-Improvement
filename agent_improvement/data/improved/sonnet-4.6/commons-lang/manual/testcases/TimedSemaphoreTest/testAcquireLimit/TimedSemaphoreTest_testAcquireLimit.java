package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testAcquireLimit extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /**
     * Helper thread that calls acquire() a fixed number of times on a semaphore,
     * counting down a latch after each of the first {@code latchCount} successful acquires.
     */
    private static final class SemaphoreThread extends Thread {
        private final TimedSemaphore semaphore;
        private final CountDownLatch latch;
        private final int count;
        private final int latchCount;

        SemaphoreThread(final TimedSemaphore semaphore, final CountDownLatch latch, final int count, final int latchCount) {
            this.semaphore = semaphore;
            this.latch = latch;
            this.count = count;
            this.latchCount = latchCount;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < count; i++) {
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

    private void prepareStartTimer(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS),
                EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Verifies that acquire() blocks once the per-period limit is reached, then
     * resumes after endOfPeriod() resets the counter.
     *
     * Scenario: semaphore allows 9 acquires per period. A background thread attempts
     * 10 total acquires. It should complete the first 9, block on the 10th, and only
     * finish once the period is manually reset via endOfPeriod().
     */
    @Test
    void testAcquireLimit() throws InterruptedException {
        final ScheduledExecutorService mockExecutor = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> mockFuture = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(mockExecutor, mockFuture);
        EasyMock.replay(mockExecutor, mockFuture);

        final int totalAcquires = 10;
        final int perPeriodLimit = totalAcquires - 1; // 9: the thread blocks before the 10th

        // Latch signals when the thread has completed exactly perPeriodLimit acquires
        final CountDownLatch limitReachedLatch = new CountDownLatch(perPeriodLimit);
        final TimedSemaphore semaphore = new TimedSemaphore(mockExecutor, PERIOD_MILLIS, UNIT, 1);
        semaphore.setLimit(perPeriodLimit);

        // Thread calls acquire() totalAcquires times; latch counts down on each of the first perPeriodLimit calls
        final SemaphoreThread acquirerThread =
                new SemaphoreThread(semaphore, limitReachedLatch, totalAcquires, perPeriodLimit);
        acquirerThread.start();

        // Wait until the background thread is blocked at the per-period limit
        limitReachedLatch.await();
        assertEquals(perPeriodLimit, semaphore.getAcquireCount(),
                "Acquire count must equal the per-period limit while the thread is blocked");

        // Simulate the scheduler firing: resets the counter and unblocks the waiting thread
        semaphore.endOfPeriod();
        acquirerThread.join();

        // The thread completed its final acquire in the new period, so only 1 acquire is counted now
        assertEquals(1, semaphore.getAcquireCount(),
                "Only the single post-reset acquire should appear in the new period");
        // All acquires from the previous period are captured by getLastAcquiresPerPeriod()
        assertEquals(perPeriodLimit, semaphore.getLastAcquiresPerPeriod(),
                "The previous period should record exactly perPeriodLimit acquires");

        EasyMock.verify(mockExecutor, mockFuture);
    }
}
