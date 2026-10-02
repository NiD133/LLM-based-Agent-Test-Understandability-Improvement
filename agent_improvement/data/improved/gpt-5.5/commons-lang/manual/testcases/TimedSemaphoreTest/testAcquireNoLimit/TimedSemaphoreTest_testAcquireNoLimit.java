package org.apache.commons.lang3.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testAcquireNoLimit extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final TimeUnit PERIOD_UNIT = TimeUnit.MILLISECONDS;
    private static final int ACQUIRE_ATTEMPTS = 1000;

    /**
     * Prepares the executor mock for the timer that starts on the first acquire.
     */
    private void expectTimerStart(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    private static final class TimedSemaphoreTestImpl extends TimedSemaphore {

        @SuppressWarnings("deprecation")
        TimedSemaphoreTestImpl(final ScheduledExecutorService service, final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(service, timePeriod, timeUnit, limit);
        }
    }

    private static final class SemaphoreThread extends Thread {

        private final TimedSemaphore semaphore;
        private final CountDownLatch latch;
        private final int acquireCount;

        SemaphoreThread(final TimedSemaphore semaphore, final CountDownLatch latch, final int acquireCount, final int ignoredExpectedCount) {
            this.semaphore = semaphore;
            this.latch = latch;
            this.acquireCount = acquireCount;
        }

        @Override
        public void run() {
            for (int i = 0; i < acquireCount; i++) {
                try {
                    semaphore.acquire();
                    latch.countDown();
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    /**
     * Tests that an unlimited semaphore allows many acquire() calls without waiting
     * for the period to end.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testAcquireNoLimit() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        expectTimerStart(service, future);
        EasyMock.replay(service, future);

        final TimedSemaphoreTestImpl semaphore =
                new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, PERIOD_UNIT, TimedSemaphore.NO_LIMIT);
        final CountDownLatch allAcquiresCompleted = new CountDownLatch(ACQUIRE_ATTEMPTS);
        final SemaphoreThread acquiringThread =
                new SemaphoreThread(semaphore, allAcquiresCompleted, ACQUIRE_ATTEMPTS, ACQUIRE_ATTEMPTS);

        acquiringThread.start();
        allAcquiresCompleted.await();

        EasyMock.verify(service, future);
    }
}
