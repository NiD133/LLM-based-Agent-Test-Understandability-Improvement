package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testTryAcquire extends AbstractLangTest {

    /** Maximum number of permits allowed per time period. */
    private static final int LIMIT = 10;

    /** Length of the semaphore's time window, expressed in the unit used by the constructor. */
    private static final long PERIOD_MILLIS = 500;

    /**
     * A thread that attempts a single non-blocking {@code tryAcquire()} on a
     * {@link TimedSemaphore} and records whether the attempt succeeded.
     *
     * <p>All threads wait on a shared {@code startSignal} latch before calling
     * {@code tryAcquire()}, so they all compete simultaneously within the same
     * time period.</p>
     */
    private static final class TryAcquireThread extends Thread {

        private final TimedSemaphore semaphore;
        private final CountDownLatch startSignal;

        /** Set to {@code true} when {@code tryAcquire()} returned {@code true}. */
        boolean acquired;

        TryAcquireThread(final TimedSemaphore semaphore, final CountDownLatch startSignal) {
            this.semaphore = semaphore;
            this.startSignal = startSignal;
        }

        @Override
        public void run() {
            try {
                if (startSignal.await(10, TimeUnit.SECONDS)) {
                    acquired = semaphore.tryAcquire();
                }
            } catch (final InterruptedException e) {
                // ignore — thread simply leaves acquired=false
            }
        }
    }

    /**
     * Verifies that {@code tryAcquire()} grants at most {@code LIMIT} permits
     * within a single time period, even when many more threads compete.
     *
     * <p>Strategy: launch three times as many threads as the permit limit and
     * release them all simultaneously via a latch. Because {@code tryAcquire()}
     * never blocks, each thread either secures a permit immediately or is turned
     * away. After all threads finish, the count of successful acquires must be
     * exactly {@code LIMIT}.</p>
     */
    @Test
    void testTryAcquire() throws InterruptedException {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, TimeUnit.SECONDS, LIMIT);

        // Use 3× the limit so that more threads contend than the semaphore allows.
        final int threadCount = 3 * LIMIT;
        final CountDownLatch startSignal = new CountDownLatch(1);

        final TryAcquireThread[] threads = new TryAcquireThread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new TryAcquireThread(semaphore, startSignal);
            threads[i].start();
        }

        // Release all threads at once so they race for permits in the same period.
        startSignal.countDown();

        int permitsGranted = 0;
        for (final TryAcquireThread t : threads) {
            t.join();
            if (t.acquired) {
                permitsGranted++;
            }
        }

        assertEquals(LIMIT, permitsGranted, "Wrong number of permits granted");
    }
}
