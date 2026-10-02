package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link TimedSemaphore#tryAcquire()}.
 */
public class TimedSemaphoreTest_testTryAcquire extends AbstractLangTest {

    /** Length of the time period monitored by the semaphore. */
    private static final long PERIOD = 500;

    /** Maximum number of permits the semaphore grants per period. */
    private static final int LIMIT = 10;

    /** Number of competing threads: three times the limit, so most will be refused. */
    private static final int THREAD_COUNT = 3 * LIMIT;

    /**
     * A worker thread that waits on a shared start signal and then performs a single
     * {@link TimedSemaphore#tryAcquire()}, recording whether it obtained a permit.
     */
    private static final class TryAcquireThread extends Thread {

        private final TimedSemaphore semaphore;
        private final CountDownLatch startSignal;

        /** Whether this thread successfully acquired a permit. */
        private boolean acquired;

        TryAcquireThread(final TimedSemaphore semaphore, final CountDownLatch startSignal) {
            this.semaphore = semaphore;
            this.startSignal = startSignal;
        }

        @Override
        public void run() {
            try {
                // Wait for the test to release all threads at once, then make a single attempt.
                if (startSignal.await(10, TimeUnit.SECONDS)) {
                    acquired = semaphore.tryAcquire();
                }
            } catch (final InterruptedException iex) {
                // Ignore: a non-acquiring thread simply leaves "acquired" as false.
            }
        }
    }

    /**
     * Tests {@code tryAcquire()}: when many threads contend for the semaphore at the same
     * time, exactly {@code LIMIT} of them should obtain a permit and no more.
     */
    @Test
    void testTryAcquire() throws InterruptedException {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD, TimeUnit.SECONDS, LIMIT);

        // A single latch lets every thread start its tryAcquire() at roughly the same moment,
        // maximizing contention within one period.
        final CountDownLatch startSignal = new CountDownLatch(1);
        final TryAcquireThread[] threads = new TryAcquireThread[THREAD_COUNT];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new TryAcquireThread(semaphore, startSignal);
            threads[i].start();
        }

        // Release all threads simultaneously.
        startSignal.countDown();

        // Wait for every thread to finish and count how many acquired a permit.
        int grantedPermits = 0;
        for (final TryAcquireThread thread : threads) {
            thread.join();
            if (thread.acquired) {
                grantedPermits++;
            }
        }

        assertEquals(LIMIT, grantedPermits, "Wrong number of permits granted");
    }
}
