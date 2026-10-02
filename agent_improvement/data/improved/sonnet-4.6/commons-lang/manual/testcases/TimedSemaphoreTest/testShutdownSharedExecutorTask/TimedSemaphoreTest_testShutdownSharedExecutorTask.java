package org.apache.commons.lang3.concurrent;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.ThreadUtils;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testShutdownSharedExecutorTask extends AbstractLangTest {

    /**
     * A specialized subclass of {@link TimedSemaphore} that allows injecting a mock
     * {@link ScheduledFuture} so tests can verify timer-task interactions without
     * running real scheduled threads.
     */
    private static final class TimedSemaphoreTestImpl extends TimedSemaphore {

        /** When set, returned by {@link #startTimer()} instead of scheduling a real task. */
        ScheduledFuture<?> schedFuture;

        /** Optional latch counted down on each {@link #acquire()} for thread synchronization. */
        volatile CountDownLatch latch;

        /** Counts how many times {@link #endOfPeriod()} has been invoked. */
        private int periodEnds;

        TimedSemaphoreTestImpl(final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(timePeriod, timeUnit, limit);
        }

        TimedSemaphoreTestImpl(final ScheduledExecutorService service, final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(service, timePeriod, timeUnit, limit);
        }

        @Override
        public synchronized void acquire() throws InterruptedException {
            super.acquire();
            if (latch != null) {
                latch.countDown();
            }
        }

        @Override
        protected synchronized void endOfPeriod() {
            super.endOfPeriod();
            periodEnds++;
        }

        int getPeriodEnds() {
            synchronized (this) {
                return periodEnds;
            }
        }

        /** Returns the injected mock future if set; otherwise delegates to the real timer. */
        @Override
        protected ScheduledFuture<?> startTimer() {
            return schedFuture != null ? schedFuture : super.startTimer();
        }
    }

    private static final long PERIOD_MILLIS = 500;

    private static final Duration DURATION = Duration.ofMillis(PERIOD_MILLIS);

    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    private static final int LIMIT = 10;

    /**
     * Registers the expectation that the given executor service will be asked to
     * schedule a periodic timer task, and that it will return the given future.
     * This matches the internal call made by {@link TimedSemaphore} the first
     * time {@code acquire()} is invoked.
     *
     * @param service the mock executor service
     * @param future  the mock future that the service should return
     */
    private void prepareStartTimer(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Tests that {@code shutdown()} cancels the periodic timer task when the
     * semaphore was constructed with a shared (externally-owned) executor service
     * and the timer task had already been started by a prior {@code acquire()} call.
     *
     * <p>Because the executor is shared (not owned by the semaphore), the semaphore
     * must <em>not</em> shut down the executor itself — it must only cancel the
     * scheduled task via {@code future.cancel(false)}.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testShutdownSharedExecutorTask() throws InterruptedException {
        // Given: a shared executor and a future that represents the running timer task.
        // The semaphore will schedule a fixed-rate task on acquire(), then cancel it on shutdown().
        final ScheduledExecutorService sharedExecutorService = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> timerTaskFuture = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(sharedExecutorService, timerTaskFuture);
        // cancel(false) is expected: the task should be cancelled without interrupting a running execution
        EasyMock.expect(Boolean.valueOf(timerTaskFuture.cancel(false))).andReturn(Boolean.TRUE);
        EasyMock.replay(sharedExecutorService, timerTaskFuture);

        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(sharedExecutorService, PERIOD_MILLIS, UNIT, LIMIT);

        // When: acquire() starts the timer task, then shutdown() is called
        semaphore.acquire();
        semaphore.shutdown();

        // Then: the semaphore is in shutdown state and the timer task was cancelled on the mock
        assertTrue(semaphore.isShutdown(), "Not shutdown");
        EasyMock.verify(sharedExecutorService, timerTaskFuture);
    }
}
