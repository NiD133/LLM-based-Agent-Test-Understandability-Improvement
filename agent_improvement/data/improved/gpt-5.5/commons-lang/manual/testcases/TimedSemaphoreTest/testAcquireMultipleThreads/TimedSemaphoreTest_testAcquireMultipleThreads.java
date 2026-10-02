package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testAcquireMultipleThreads extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;
    private static final int THREAD_COUNT = 10;
    private static final int PERMITS_PER_PERIOD = 1;
    private static final int EXPECTED_ACQUIRES_PER_PERIOD = 1;

    private void prepareStartTimer(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    @Test
    void testAcquireMultipleThreads() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);

        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, PERMITS_PER_PERIOD);
        semaphore.latch = new CountDownLatch(1);

        final SemaphoreThread[] threads = startSemaphoreThreads(semaphore);
        verifyOneThreadAcquiresPermitInEachPeriod(semaphore);
        waitForThreadsToFinish(threads);

        EasyMock.verify(service, future);
    }

    private SemaphoreThread[] startSemaphoreThreads(final TimedSemaphoreTestImpl semaphore) {
        final SemaphoreThread[] threads = new SemaphoreThread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new SemaphoreThread(semaphore, null, 1, 0);
            threads[i].start();
        }
        return threads;
    }

    private void verifyOneThreadAcquiresPermitInEachPeriod(final TimedSemaphoreTestImpl semaphore) throws InterruptedException {
        for (int i = 0; i < THREAD_COUNT; i++) {
            semaphore.latch.await();
            assertEquals(EXPECTED_ACQUIRES_PER_PERIOD, semaphore.getAcquireCount(), "Wrong count");
            semaphore.latch = new CountDownLatch(1);
            semaphore.endOfPeriod();
            assertEquals(EXPECTED_ACQUIRES_PER_PERIOD, semaphore.getLastAcquiresPerPeriod(), "Wrong acquire count");
        }
    }

    private void waitForThreadsToFinish(final SemaphoreThread[] threads) throws InterruptedException {
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
        }
    }

    private static class TimedSemaphoreTestImpl extends TimedSemaphore {

        private volatile CountDownLatch latch;

        TimedSemaphoreTestImpl(final ScheduledExecutorService service, final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(service, timePeriod, timeUnit, limit);
        }

        @Override
        public void acquire() throws InterruptedException {
            super.acquire();
            latch.countDown();
        }
    }

    private static class SemaphoreThread extends Thread {

        private final TimedSemaphore semaphore;
        private final CountDownLatch latch;
        private final int acquireCount;
        private final long sleepMillis;

        SemaphoreThread(final TimedSemaphore semaphore, final CountDownLatch latch, final int acquireCount, final long sleepMillis) {
            this.semaphore = semaphore;
            this.latch = latch;
            this.acquireCount = acquireCount;
            this.sleepMillis = sleepMillis;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < acquireCount; i++) {
                    semaphore.acquire();
                    if (latch != null) {
                        latch.countDown();
                    }
                    if (sleepMillis > 0) {
                        Thread.sleep(sleepMillis);
                    }
                }
            } catch (final InterruptedException ex) {
                interrupt();
            }
        }
    }
}
