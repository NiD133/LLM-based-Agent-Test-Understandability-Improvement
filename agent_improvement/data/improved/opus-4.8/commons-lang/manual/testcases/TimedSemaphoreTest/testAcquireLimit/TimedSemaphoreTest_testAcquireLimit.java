package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.concurrent.TimedSemaphoreTest.SemaphoreThread;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TimedSemaphore#acquire()} blocks the calling thread once the
 * configured limit for the current period has been reached, and lets it proceed
 * again after the period ends.
 */
public class TimedSemaphoreTest_testAcquireLimit extends AbstractLangTest {

    /** The length of the monitored time period. */
    private static final long PERIOD_MILLIS = 500;

    /** The time unit used for the period. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** Number of times the worker thread attempts to acquire the semaphore. */
    private static final int ACQUIRE_ATTEMPTS = 10;

    /** Permits allowed per period; one fewer than the number of attempts. */
    private static final int LIMIT = ACQUIRE_ATTEMPTS - 1;

    /**
     * Configures the executor-service mock to expect the semaphore to schedule its
     * periodic timer task, returning the supplied future.
     *
     * @param service the mock executor service.
     * @param future  the future the scheduled task should return.
     */
    private void expectTimerScheduled(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Tests {@link TimedSemaphore#acquire()} when a limit is set: after {@code LIMIT}
     * successful acquisitions the worker thread blocks, and it is only released once
     * the current period ends.
     *
     * @throws InterruptedException so we don't have to catch it.
     */
    @Test
    void testAcquireLimit() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        expectTimerScheduled(service, future);
        EasyMock.replay(service, future);

        // The latch reaches zero once the worker has made its LIMIT permitted acquisitions.
        final CountDownLatch limitReached = new CountDownLatch(LIMIT);
        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, 1);
        final SemaphoreThread worker = new SemaphoreThread(semaphore, limitReached, ACQUIRE_ATTEMPTS, LIMIT);
        semaphore.setLimit(LIMIT);

        // Start the worker, which calls acquire() ACQUIRE_ATTEMPTS times.
        worker.start();
        limitReached.await();

        // The limit is now exhausted, so the worker is blocked on its last acquire().
        assertEquals(LIMIT, semaphore.getAcquireCount(), "Wrong semaphore count");

        // Ending the period wakes the worker, which performs its final acquire().
        semaphore.endOfPeriod();
        worker.join();

        assertEquals(1, semaphore.getAcquireCount(), "Wrong semaphore count (2)");
        assertEquals(LIMIT, semaphore.getLastAcquiresPerPeriod(), "Wrong acquire() count");
        EasyMock.verify(service, future);
    }
}
