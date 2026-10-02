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
 * Tests {@link TimedSemaphore#acquire()} when more threads compete for permits
 * than the per-period limit allows.
 */
public class TimedSemaphoreTest_testAcquireMultipleThreads extends AbstractLangTest {

    /** The monitored time period for the semaphore. */
    private static final long PERIOD_MILLIS = 500;

    /** The time unit used for {@link #PERIOD_MILLIS}. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /**
     * Configures the executor-service mock to expect that the semaphore schedules
     * its periodic timer task exactly once, returning the supplied future.
     *
     * @param service the executor-service mock to program.
     * @param future  the future the scheduled task should return.
     */
    private void prepareStartTimer(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Tests the acquire() method if more threads are involved than the limit.
     * This method starts a number of threads that all invoke the semaphore. The
     * semaphore's limit is set to 1, so in each period only a single thread can
     * acquire the semaphore.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testAcquireMultipleThreads() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);

        // Limit of 1: only one thread can pass the semaphore per period.
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, 1);
        semaphore.latch = new CountDownLatch(1);

        final int threadCount = 10;
        final SemaphoreThread[] threads = new SemaphoreThread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new SemaphoreThread(semaphore, null, 1, 0);
            threads[i].start();
        }

        // Release the threads one period at a time. In each period exactly one
        // thread acquires a permit; ending the period frees up the next one.
        for (int i = 0; i < threadCount; i++) {
            semaphore.latch.await();
            assertEquals(1, semaphore.getAcquireCount(), "Wrong count");
            semaphore.latch = new CountDownLatch(1);
            semaphore.endOfPeriod();
            assertEquals(1, semaphore.getLastAcquiresPerPeriod(), "Wrong acquire count");
        }

        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }
        EasyMock.verify(service, future);
    }

    /**
     * A thread that invokes {@link TimedSemaphore#acquire()} a fixed number of
     * times. After a configurable number of invocations it can notify the main
     * thread through a latch.
     */
    private static final class SemaphoreThread extends Thread {

        /** The semaphore under test. */
        private final TimedSemaphore semaphore;

        /** An optional latch used to synchronize with the main thread. */
        private final CountDownLatch latch;

        /** The number of acquire() calls to perform. */
        private final int count;

        /** The acquire() invocation after which the latch is triggered. */
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
                    if (latch != null && i == latchCount) {
                        latch.countDown();
                    }
                }
            } catch (final InterruptedException iex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * A {@link TimedSemaphore} subclass that signals the main test thread through
     * a latch each time a permit is acquired, so the test can step through the
     * periods deterministically.
     */
    private static final class TimedSemaphoreTestImpl extends TimedSemaphore {

        /** A latch that is counted down whenever a permit is acquired. */
        volatile CountDownLatch latch;

        TimedSemaphoreTestImpl(final ScheduledExecutorService service, final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(service, timePeriod, timeUnit, limit);
        }

        @Override
        public void acquire() throws InterruptedException {
            super.acquire();
            if (latch != null) {
                latch.countDown();
            }
        }
    }
}
