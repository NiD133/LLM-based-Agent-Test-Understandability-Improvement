package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testAcquireMultiplePeriods extends AbstractLangTest {

    /**
     * Constant for the time period.
     */
    private static final long PERIOD_MILLIS = 500;

    /**
     * Tests a bigger number of invocations that span multiple periods. The
     * period is set to a very short time. A background thread calls the
     * semaphore a large number of times. While it runs at last one end of a
     * period should be reached.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testAcquireMultiplePeriods() throws InterruptedException {
        final int acquireAttempts = 1000;
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(PERIOD_MILLIS / 10, TimeUnit.MILLISECONDS, 1);
        semaphore.setLimit(acquireAttempts / 4);

        final CountDownLatch allAcquiresCompleted = new CountDownLatch(acquireAttempts);
        final SemaphoreThread acquiringThread = new SemaphoreThread(semaphore, allAcquiresCompleted, acquireAttempts, acquireAttempts);
        acquiringThread.start();
        allAcquiresCompleted.await();

        semaphore.shutdown();
        assertTrue(semaphore.getPeriodEnds() > 0, "End of period not reached");
    }

    private static class SemaphoreThread extends Thread {

        private final CountDownLatch latch;
        private final int latchCount;
        private final int loopCount;
        private final TimedSemaphore semaphore;

        SemaphoreThread(final TimedSemaphore semaphore, final CountDownLatch latch, final int loopCount, final int latchCount) {
            this.semaphore = semaphore;
            this.latch = latch;
            this.loopCount = loopCount;
            this.latchCount = latchCount;
        }

        @Override
        public void run() {
            for (int i = 0; i < loopCount; i++) {
                try {
                    semaphore.acquire();
                    if (i < latchCount) {
                        latch.countDown();
                    }
                } catch (final InterruptedException e) {
                    interrupt();
                    return;
                }
            }
        }
    }

    private static class TimedSemaphoreTestImpl extends TimedSemaphore {

        private int periodEnds;

        @SuppressWarnings("deprecation")
        TimedSemaphoreTestImpl(final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(timePeriod, timeUnit, limit);
        }

        int getPeriodEnds() {
            return periodEnds;
        }

        @Override
        synchronized void endOfPeriod() {
            periodEnds++;
            super.endOfPeriod();
        }
    }
}
