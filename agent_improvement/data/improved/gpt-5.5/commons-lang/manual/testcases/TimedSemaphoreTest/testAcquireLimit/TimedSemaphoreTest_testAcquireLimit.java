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
    private static final int INITIAL_LIMIT = 1;
    private static final int ACQUIRE_ATTEMPTS = 10;
    private static final int ACQUIRES_ALLOWED_PER_PERIOD = ACQUIRE_ATTEMPTS - 1;

    private void expectTimerStart(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Tests the acquire() method if a limit is set.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testAcquireLimit() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        expectTimerStart(service, future);
        EasyMock.replay(service, future);

        final CountDownLatch firstPeriodAcquiresCompleted = new CountDownLatch(ACQUIRES_ALLOWED_PER_PERIOD);
        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, INITIAL_LIMIT);
        final SemaphoreThread acquiringThread = new SemaphoreThread(semaphore, firstPeriodAcquiresCompleted, ACQUIRE_ATTEMPTS, ACQUIRES_ALLOWED_PER_PERIOD);
        semaphore.setLimit(ACQUIRES_ALLOWED_PER_PERIOD);

        acquiringThread.start();
        firstPeriodAcquiresCompleted.await();

        assertEquals(ACQUIRES_ALLOWED_PER_PERIOD, semaphore.getAcquireCount(), "Wrong semaphore count");

        semaphore.endOfPeriod();
        acquiringThread.join();

        assertEquals(1, semaphore.getAcquireCount(), "Wrong semaphore count (2)");
        assertEquals(ACQUIRES_ALLOWED_PER_PERIOD, semaphore.getLastAcquiresPerPeriod(), "Wrong acquire() count");
        EasyMock.verify(service, future);
    }

    private static final class SemaphoreThread extends Thread {

        private final TimedSemaphore semaphore;
        private final CountDownLatch latch;
        private final int acquireAttempts;
        private final int latchCountdowns;

        private SemaphoreThread(final TimedSemaphore semaphore, final CountDownLatch latch, final int acquireAttempts, final int latchCountdowns) {
            this.semaphore = semaphore;
            this.latch = latch;
            this.acquireAttempts = acquireAttempts;
            this.latchCountdowns = latchCountdowns;
        }

        @Override
        public void run() {
            for (int i = 0; i < acquireAttempts; i++) {
                try {
                    semaphore.acquire();
                    if (i < latchCountdowns) {
                        latch.countDown();
                    }
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}
