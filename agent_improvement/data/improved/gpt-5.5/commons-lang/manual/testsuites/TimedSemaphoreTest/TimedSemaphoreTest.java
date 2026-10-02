/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/**
 * Tests {@link TimedSemaphore}.
 */
class TimedSemaphoreTest extends AbstractLangTest {

    /**
     * Calls {@link TimedSemaphore#acquire()} a configured number of times and
     * optionally counts down a latch after the first calls.
     */
    private static final class SemaphoreThread extends Thread {

        private final TimedSemaphore semaphore;

        private final CountDownLatch latch;

        private final int acquireCount;

        private final int latchCount;

        SemaphoreThread(final TimedSemaphore semaphore, final CountDownLatch latch, final int acquireCount, final int latchCount) {
            this.semaphore = semaphore;
            this.latch = latch;
            this.acquireCount = acquireCount;
            this.latchCount = latchCount;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < acquireCount; i++) {
                    semaphore.acquire();

                    if (i < latchCount) {
                        latch.countDown();
                    }
                }
            } catch (final InterruptedException iex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Test-specific {@link TimedSemaphore} that exposes period-end counting and
     * can return a mocked timer future.
     */
    private static final class TimedSemaphoreTestImpl extends TimedSemaphore {

        ScheduledFuture<?> schedFuture;

        volatile CountDownLatch latch;

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

        @Override
        protected ScheduledFuture<?> startTimer() {
            return schedFuture != null ? schedFuture : super.startTimer();
        }
    }

    /**
     * Invokes {@link TimedSemaphore#tryAcquire()} after a latch is released and
     * stores the result.
     */
    private static final class TryAcquireThread extends Thread {

        private final TimedSemaphore semaphore;

        private final CountDownLatch latch;

        private boolean acquired;

        TryAcquireThread(final TimedSemaphore semaphore, final CountDownLatch latch) {
            this.semaphore = semaphore;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                if (latch.await(10, TimeUnit.SECONDS)) {
                    acquired = semaphore.tryAcquire();
                }
            } catch (final InterruptedException iex) {
                // Preserve the original test behavior: ignore interruptions here.
            }
        }
    }

    private static final long PERIOD_MILLIS = 500;

    private static final Duration DURATION = Duration.ofMillis(PERIOD_MILLIS);

    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    private static final int LIMIT = 10;

    /**
     * Prepares an executor service mock to expect the timer task registration.
     *
     * @param service the mock service
     * @param future the scheduled future returned by the mock service
     */
    private void prepareStartTimer(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    @Test
    void testAcquireLimit() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);
        final int count = 10;
        final CountDownLatch latch = new CountDownLatch(count - 1);
        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, 1);
        final SemaphoreThread t = new SemaphoreThread(semaphore, latch, count, count - 1);
        semaphore.setLimit(count - 1);
        t.start();
        latch.await();
        assertEquals(count - 1, semaphore.getAcquireCount(), "Wrong semaphore count");
        semaphore.endOfPeriod();
        t.join();
        assertEquals(1, semaphore.getAcquireCount(), "Wrong semaphore count (2)");
        assertEquals(count - 1, semaphore.getLastAcquiresPerPeriod(), "Wrong acquire() count");
        EasyMock.verify(service, future);
    }

    @Test
    void testAcquireMultiplePeriods() throws InterruptedException {
        final int count = 1000;
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(PERIOD_MILLIS / 10, TimeUnit.MILLISECONDS, 1);
        semaphore.setLimit(count / 4);
        final CountDownLatch latch = new CountDownLatch(count);
        final SemaphoreThread t = new SemaphoreThread(semaphore, latch, count, count);
        t.start();
        latch.await();
        semaphore.shutdown();
        assertTrue(semaphore.getPeriodEnds() > 0, "End of period not reached");
    }

    @Test
    void testAcquireMultipleThreads() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, 1);
        semaphore.latch = new CountDownLatch(1);
        final int count = 10;
        final SemaphoreThread[] threads = new SemaphoreThread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new SemaphoreThread(semaphore, null, 1, 0);
            threads[i].start();
        }
        for (int i = 0; i < count; i++) {
            semaphore.latch.await();
            assertEquals(1, semaphore.getAcquireCount(), "Wrong count");
            semaphore.latch = new CountDownLatch(1);
            semaphore.endOfPeriod();
            assertEquals(1, semaphore.getLastAcquiresPerPeriod(), "Wrong acquire count");
        }
        for (int i = 0; i < count; i++) {
            threads[i].join();
        }
        EasyMock.verify(service, future);
    }

    @Test
    void testAcquireNoLimit() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, TimedSemaphore.NO_LIMIT);
        final int count = 1000;
        final CountDownLatch latch = new CountDownLatch(count);
        final SemaphoreThread t = new SemaphoreThread(semaphore, latch, count, count);
        t.start();
        latch.await();
        EasyMock.verify(service, future);
    }

    @Test
    void testGetAvailablePermits() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);
        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);
        for (int i = 0; i < LIMIT; i++) {
            assertEquals(LIMIT - i, semaphore.getAvailablePermits(), "Wrong available count at " + i);
            semaphore.acquire();
        }
        semaphore.endOfPeriod();
        assertEquals(LIMIT, semaphore.getAvailablePermits(), "Wrong available count in new period");
        EasyMock.verify(service, future);
    }

    @Test
    void testGetAverageCallsPerPeriod() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);
        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.acquire();
        semaphore.endOfPeriod();
        assertEquals(1.0, semaphore.getAverageCallsPerPeriod(), .005, "Wrong average (1)");
        semaphore.acquire();
        semaphore.acquire();
        semaphore.endOfPeriod();
        assertEquals(1.5, semaphore.getAverageCallsPerPeriod(), .005, "Wrong average (2)");
        EasyMock.verify(service, future);
    }

    @Test
    void testInit() {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        EasyMock.replay(service);
        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);
        EasyMock.verify(service);
        assertEquals(service, semaphore.getExecutorService(), "Wrong service");
        assertEquals(PERIOD_MILLIS, semaphore.getPeriod(), "Wrong period");
        assertEquals(UNIT, semaphore.getUnit(), "Wrong unit");
        assertEquals(0, semaphore.getLastAcquiresPerPeriod(), "Statistic available");
        assertEquals(0.0, semaphore.getAverageCallsPerPeriod(), .05, "Average available");
        assertFalse(semaphore.isShutdown(), "Already shutdown");
        assertEquals(LIMIT, semaphore.getLimit(), "Wrong limit");
    }

    @Test
    void testInitDefaultService() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        final ScheduledThreadPoolExecutor exec = (ScheduledThreadPoolExecutor) semaphore.getExecutorService();
        assertFalse(exec.getContinueExistingPeriodicTasksAfterShutdownPolicy(), "Wrong periodic task policy");
        assertFalse(exec.getExecuteExistingDelayedTasksAfterShutdownPolicy(), "Wrong delayed task policy");
        assertFalse(exec.isShutdown(), "Already shutdown");
        semaphore.shutdown();
    }

    @Test
    void testInitInvalidPeriod() {
        assertIllegalArgumentException(() -> new TimedSemaphore(0L, UNIT, LIMIT));
    }

    @Test
    void testPassAfterShutdown() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();
        assertThrows(IllegalStateException.class, semaphore::acquire);
    }

    @Test
    void testShutdownMultipleTimes() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.expect(Boolean.valueOf(future.cancel(false))).andReturn(Boolean.TRUE);
        EasyMock.replay(service, future);
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.acquire();
        for (int i = 0; i < 10; i++) {
            semaphore.shutdown();
        }
        EasyMock.verify(service, future);
    }

    @Test
    void testShutdownOwnExecutor() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();
        assertTrue(semaphore.isShutdown(), "Not shutdown");
        assertTrue(semaphore.getExecutorService().isShutdown(), "Executor not shutdown");
    }

    @Test
    void testShutdownSharedExecutorNoTask() {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        EasyMock.replay(service);
        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();
        assertTrue(semaphore.isShutdown(), "Not shutdown");
        EasyMock.verify(service);
    }

    @Test
    void testShutdownSharedExecutorTask() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.expect(Boolean.valueOf(future.cancel(false))).andReturn(Boolean.TRUE);
        EasyMock.replay(service, future);
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.acquire();
        semaphore.shutdown();
        assertTrue(semaphore.isShutdown(), "Not shutdown");
        EasyMock.verify(service, future);
    }

    /**
     * Verifies that {@link TimedSemaphore#shutdown()} wakes a thread blocked in
     * {@link TimedSemaphore#acquire()} when the current period's only permit is
     * already consumed. The long period keeps the timer from waking the blocked
     * thread during this test window; shutdown must be the wake-up source.
     */
    @Test
    public void testShutdownWakesBlockedAcquireThreads() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            final TimedSemaphore sem = TimedSemaphore.builder().setPeriod(60).setTimeUnit(TimeUnit.SECONDS).setLimit(1).get();
            sem.acquire();
            final Thread blocker = new Thread(() -> {
                try {
                    sem.acquire();
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (final IllegalStateException e) {
                    // Expected after shutdown wakes the blocked acquire() call.
                }
            }, "testShutdownWakesBlockedAcquireThreads");
            blocker.setDaemon(true);
            blocker.start();
            final long parkDeadline = System.nanoTime() + Duration.ofSeconds(2).toNanos();
            while (System.nanoTime() < parkDeadline && blocker.getState() != Thread.State.WAITING) {
                Thread.sleep(10);
            }
            sem.shutdown();
            blocker.join(5000);
            assertFalse(blocker.isAlive(), "TimedSemaphore.shutdown() failed to wake thread blocked in acquire(): blocker still alive in state="
                    + blocker.getState() + " 5s after shutdown(). Bug present (shutdown() does not call notifyAll()).");
        });
    }

    @Test
    void testStartTimer() throws InterruptedException {
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(PERIOD_MILLIS, UNIT, LIMIT);
        final ScheduledFuture<?> future = semaphore.startTimer();
        assertNotNull(future, "No future returned");
        ThreadUtils.sleepQuietly(DURATION);
        final int trials = 10;
        int count = 0;
        do {
            Thread.sleep(PERIOD_MILLIS);
            assertFalse(count++ > trials, "endOfPeriod() not called!");
        } while (semaphore.getPeriodEnds() <= 0);
        semaphore.shutdown();
    }

    @Test
    void testTryAcquire() throws InterruptedException {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, TimeUnit.SECONDS, LIMIT);
        final TryAcquireThread[] threads = new TryAcquireThread[3 * LIMIT];
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new TryAcquireThread(semaphore, latch);
            threads[i].start();
        }
        latch.countDown();
        int permits = 0;
        for (final TryAcquireThread t : threads) {
            t.join();
            if (t.acquired) {
                permits++;
            }
        }
        assertEquals(LIMIT, permits, "Wrong number of permits granted");
    }

    @Test
    void testTryAcquireAfterShutdown() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();
        assertThrows(IllegalStateException.class, semaphore::tryAcquire);
    }
}
