package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testTryAcquire extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final int LIMIT = 10;
    private static final int THREAD_COUNT = 3 * LIMIT;

    /**
     * Tests the tryAcquire() method. It is checked whether the semaphore can be acquired
     * by a bunch of threads the expected number of times and not more.
     */
    @Test
    void testTryAcquire() throws InterruptedException {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, TimeUnit.SECONDS, LIMIT);
        final TryAcquireThread[] threads = new TryAcquireThread[THREAD_COUNT];
        final CountDownLatch startLatch = new CountDownLatch(1);

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new TryAcquireThread(semaphore, startLatch);
            threads[i].start();
        }

        startLatch.countDown();

        int permits = 0;
        for (final TryAcquireThread thread : threads) {
            thread.join();
            if (thread.acquired) {
                permits++;
            }
        }
        assertEquals(LIMIT, permits, "Wrong number of permits granted");
    }

    private static final class TryAcquireThread extends Thread {

        private final TimedSemaphore semaphore;
        private final CountDownLatch startLatch;
        private boolean acquired;

        private TryAcquireThread(final TimedSemaphore semaphore, final CountDownLatch startLatch) {
            this.semaphore = semaphore;
            this.startLatch = startLatch;
        }

        @Override
        public void run() {
            try {
                startLatch.await();
                acquired = semaphore.tryAcquire();
            } catch (final InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
