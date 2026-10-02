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
     * A test thread that calls {@link TimedSemaphore#acquire()} a configurable number of times
     * and optionally counts down a latch to synchronize with the main test thread.
     */
    private static final class SemaphoreThread extends Thread {

        private final TimedSemaphore semaphore;
        private final CountDownLatch latch;

        /** Total number of acquire() calls this thread will make. */
        private final int totalAcquires;

        /** How many of those calls should also trigger a latch.countDown(). */
        private final int latchTriggerCount;

        SemaphoreThread(final TimedSemaphore semaphore, final CountDownLatch latch,
                final int totalAcquires, final int latchTriggerCount) {
            this.semaphore = semaphore;
            this.latch = latch;
            this.totalAcquires = totalAcquires;
            this.latchTriggerCount = latchTriggerCount;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < totalAcquires; i++) {
                    semaphore.acquire();
                    if (i < latchTriggerCount) {
                        latch.countDown();
                    }
                }
            } catch (final InterruptedException iex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * A specialized {@link TimedSemaphore} subclass that replaces the real scheduler
     * with a mock future and exposes a countdown latch for synchronization, making
     * timer-driven behavior testable without real-time delays.
     */
    private static final class TimedSemaphoreTestImpl extends TimedSemaphore {

        /** Returned by startTimer() instead of scheduling a real task. */
        ScheduledFuture<?> schedFuture;

        /** Counted down inside acquire() so tests can wait for an acquire to complete. */
        volatile CountDownLatch latch;

        /** How many times endOfPeriod() has been called. */
        private int periodEnds;

        TimedSemaphoreTestImpl(final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(timePeriod, timeUnit, limit);
        }

        TimedSemaphoreTestImpl(final ScheduledExecutorService service, final long timePeriod,
                final TimeUnit timeUnit, final int limit) {
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

        /** Returns the mock future when set; otherwise delegates to the real scheduler. */
        @Override
        protected ScheduledFuture<?> startTimer() {
            return schedFuture != null ? schedFuture : super.startTimer();
        }
    }

    /**
     * A thread that calls {@link TimedSemaphore#tryAcquire()} once after receiving
     * a signal from a latch, and records whether the permit was granted.
     */
    private static final class TryAcquireThread extends Thread {

        private final TimedSemaphore semaphore;
        private final CountDownLatch startSignal;

        /** {@code true} if tryAcquire() returned {@code true}. */
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
            } catch (final InterruptedException iex) {
                // ignore
            }
        }
    }

    /** Time window in milliseconds used by most tests. */
    private static final long PERIOD_MILLIS = 500;

    private static final Duration DURATION = Duration.ofMillis(PERIOD_MILLIS);

    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** Default permit limit used when the specific value is not important. */
    private static final int LIMIT = 10;

    /**
     * Configures the mock executor service to expect a single scheduleAtFixedRate()
     * call that starts the semaphore's internal timer, returning the given future.
     */
    private void prepareStartTimer(final ScheduledExecutorService service,
            final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(),
                EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Tests that acquire() blocks once the per-period limit is reached and unblocks
     * after endOfPeriod() resets the counter.
     */
    @Test
    void testAcquireLimit() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);

        final int totalAcquires = 10;
        final int permitsPerPeriod = totalAcquires - 1;
        final CountDownLatch permitsExhaustedLatch = new CountDownLatch(permitsPerPeriod);
        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, 1);
        semaphore.setLimit(permitsPerPeriod);

        final SemaphoreThread thread = new SemaphoreThread(semaphore, permitsExhaustedLatch,
                totalAcquires, permitsPerPeriod);
        thread.start();
        // Wait until the first permitsPerPeriod acquires have completed.
        permitsExhaustedLatch.await();

        assertEquals(permitsPerPeriod, semaphore.getAcquireCount(), "Wrong semaphore count");

        // Simulate end of period; the blocked thread should now complete its last acquire.
        semaphore.endOfPeriod();
        thread.join();

        assertEquals(1, semaphore.getAcquireCount(), "Wrong semaphore count after period reset");
        assertEquals(permitsPerPeriod, semaphore.getLastAcquiresPerPeriod(), "Wrong acquire() count");
        EasyMock.verify(service, future);
    }

    /**
     * Tests that the semaphore correctly spans multiple periods. A background thread
     * calls acquire() 1000 times with a very short period so that multiple period
     * resets are guaranteed to occur.
     */
    @Test
    void testAcquireMultiplePeriods() throws InterruptedException {
        final int totalAcquires = 1000;
        final TimedSemaphoreTestImpl semaphore =
                new TimedSemaphoreTestImpl(PERIOD_MILLIS / 10, TimeUnit.MILLISECONDS, 1);
        semaphore.setLimit(totalAcquires / 4);

        final CountDownLatch allAcquiresDoneLatch = new CountDownLatch(totalAcquires);
        final SemaphoreThread thread = new SemaphoreThread(semaphore, allAcquiresDoneLatch,
                totalAcquires, totalAcquires);
        thread.start();
        allAcquiresDoneLatch.await();

        semaphore.shutdown();
        assertTrue(semaphore.getPeriodEnds() > 0, "End of period not reached");
    }

    /**
     * Tests acquire() with multiple threads competing for a single permit per period.
     * With limit=1, each period must allow exactly one thread to proceed.
     */
    @Test
    void testAcquireMultipleThreads() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);

        final TimedSemaphoreTestImpl semaphore =
                new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, 1);
        semaphore.latch = new CountDownLatch(1);

        final int threadCount = 10;
        final SemaphoreThread[] threads = new SemaphoreThread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new SemaphoreThread(semaphore, null, 1, 0);
            threads[i].start();
        }

        // Each period: wait for one acquire to complete, verify the count, then advance the period.
        for (int period = 0; period < threadCount; period++) {
            semaphore.latch.await();
            assertEquals(1, semaphore.getAcquireCount(), "Wrong count");
            semaphore.latch = new CountDownLatch(1);
            semaphore.endOfPeriod();
            assertEquals(1, semaphore.getLastAcquiresPerPeriod(), "Wrong acquire count");
        }

        for (final SemaphoreThread thread : threads) {
            thread.join();
        }
        EasyMock.verify(service, future);
    }

    /**
     * Tests that acquire() never blocks when no limit is set (NO_LIMIT).
     * A thread calls acquire() 1000 times and must finish without waiting for a period reset.
     */
    @Test
    void testAcquireNoLimit() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);

        final TimedSemaphoreTestImpl semaphore =
                new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, TimedSemaphore.NO_LIMIT);
        final int totalAcquires = 1000;
        final CountDownLatch allAcquiresDoneLatch = new CountDownLatch(totalAcquires);
        final SemaphoreThread thread = new SemaphoreThread(semaphore, allAcquiresDoneLatch,
                totalAcquires, totalAcquires);
        thread.start();
        allAcquiresDoneLatch.await();

        EasyMock.verify(service, future);
    }

    /**
     * Tests that getAvailablePermits() decreases with each acquire() and resets
     * to the full limit after endOfPeriod().
     */
    @Test
    void testGetAvailablePermits() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);

        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);
        for (int acquired = 0; acquired < LIMIT; acquired++) {
            assertEquals(LIMIT - acquired, semaphore.getAvailablePermits(),
                    "Wrong available count at " + acquired);
            semaphore.acquire();
        }

        semaphore.endOfPeriod();
        assertEquals(LIMIT, semaphore.getAvailablePermits(), "Wrong available count in new period");
        EasyMock.verify(service, future);
    }

    /**
     * Tests that getAverageCallsPerPeriod() reflects the running average across periods.
     * After period 1 with 1 acquire: average = 1.0; after period 2 with 2 acquires: average = 1.5.
     */
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

    /**
     * Tests that a new instance is initialized with the expected property values
     * and no activity recorded yet.
     */
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

    /**
     * Tests that when no executor service is provided, a default
     * {@link ScheduledThreadPoolExecutor} is created with the correct shutdown policies.
     */
    @Test
    void testInitDefaultService() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        final ScheduledThreadPoolExecutor exec =
                (ScheduledThreadPoolExecutor) semaphore.getExecutorService();
        assertFalse(exec.getContinueExistingPeriodicTasksAfterShutdownPolicy(),
                "Wrong periodic task policy");
        assertFalse(exec.getExecuteExistingDelayedTasksAfterShutdownPolicy(),
                "Wrong delayed task policy");
        assertFalse(exec.isShutdown(), "Already shutdown");
        semaphore.shutdown();
    }

    /**
     * Tests that a zero period triggers an {@link IllegalArgumentException}.
     */
    @Test
    void testInitInvalidPeriod() {
        assertIllegalArgumentException(() -> new TimedSemaphore(0L, UNIT, LIMIT));
    }

    /**
     * Tests that acquire() throws {@link IllegalStateException} after shutdown().
     */
    @Test
    void testPassAfterShutdown() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();
        assertThrows(IllegalStateException.class, semaphore::acquire);
    }

    /**
     * Tests that calling shutdown() multiple times is safe: only the first call
     * cancels the task; subsequent calls are no-ops.
     */
    @Test
    void testShutdownMultipleTimes() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.expect(Boolean.valueOf(future.cancel(false))).andReturn(Boolean.TRUE);
        EasyMock.replay(service, future);

        final TimedSemaphoreTestImpl semaphore =
                new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.acquire();
        for (int i = 0; i < 10; i++) {
            semaphore.shutdown();
        }

        EasyMock.verify(service, future);
    }

    /**
     * Tests that shutdown() also shuts down an internally created executor service.
     */
    @Test
    void testShutdownOwnExecutor() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();
        assertTrue(semaphore.isShutdown(), "Not shutdown");
        assertTrue(semaphore.getExecutorService().isShutdown(), "Executor not shutdown");
    }

    /**
     * Tests that shutdown() on a semaphore with a shared executor service that has
     * not yet started a timer task completes without interacting with the executor.
     */
    @Test
    void testShutdownSharedExecutorNoTask() {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        EasyMock.replay(service);

        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();

        assertTrue(semaphore.isShutdown(), "Not shutdown");
        EasyMock.verify(service);
    }

    /**
     * Tests that shutdown() cancels the running timer task when a shared executor
     * service is used.
     */
    @Test
    void testShutdownSharedExecutorTask() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.expect(Boolean.valueOf(future.cancel(false))).andReturn(Boolean.TRUE);
        EasyMock.replay(service, future);

        final TimedSemaphoreTestImpl semaphore =
                new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.acquire();
        semaphore.shutdown();

        assertTrue(semaphore.isShutdown(), "Not shutdown");
        EasyMock.verify(service, future);
    }

    /**
     * Tests that shutdown() wakes threads that are blocked inside acquire() waiting
     * for a permit. The semaphore uses a 60-second period so that the periodic
     * endOfPeriod() task does not fire during the test — shutdown() must call
     * notifyAll() itself to unblock the waiting thread.
     */
    @Test
    public void testShutdownWakesBlockedAcquireThreads() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            final TimedSemaphore sem = TimedSemaphore.builder()
                    .setPeriod(60).setTimeUnit(TimeUnit.SECONDS).setLimit(1).get();

            // Consume the single permit so the next acquire() will block.
            sem.acquire();

            final Thread blocker = new Thread(() -> {
                try {
                    sem.acquire();
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (final IllegalStateException e) {
                    // Expected: shutdown() woke the thread and acquire() re-checks the flag.
                }
            }, "testShutdownWakesBlockedAcquireThreads");
            blocker.setDaemon(true);
            blocker.start();

            // Wait until the blocker is parked in Object.wait() inside acquire().
            final long parkDeadline = System.nanoTime() + Duration.ofSeconds(2).toNanos();
            while (System.nanoTime() < parkDeadline && blocker.getState() != Thread.State.WAITING) {
                Thread.sleep(10);
            }

            sem.shutdown();
            blocker.join(5000);

            assertFalse(blocker.isAlive(),
                    "TimedSemaphore.shutdown() failed to wake thread blocked in acquire(): "
                    + "blocker still alive in state=" + blocker.getState()
                    + " 5s after shutdown(). Bug present (shutdown() does not call notifyAll()).");
        });
    }

    /**
     * Tests that startTimer() schedules a recurring task that eventually triggers
     * endOfPeriod().
     */
    @Test
    void testStartTimer() throws InterruptedException {
        final TimedSemaphoreTestImpl semaphore =
                new TimedSemaphoreTestImpl(PERIOD_MILLIS, UNIT, LIMIT);
        final ScheduledFuture<?> future = semaphore.startTimer();
        assertNotNull(future, "No future returned");

        ThreadUtils.sleepQuietly(DURATION);
        final int maxTrials = 10;
        int trials = 0;
        do {
            Thread.sleep(PERIOD_MILLIS);
            assertFalse(trials++ > maxTrials, "endOfPeriod() not called!");
        } while (semaphore.getPeriodEnds() <= 0);

        semaphore.shutdown();
    }

    /**
     * Tests that tryAcquire() grants exactly {@code LIMIT} permits when more threads
     * than the limit compete simultaneously.
     */
    @Test
    void testTryAcquire() throws InterruptedException {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, TimeUnit.SECONDS, LIMIT);
        final int threadCount = 3 * LIMIT;
        final TryAcquireThread[] threads = new TryAcquireThread[threadCount];
        final CountDownLatch startSignal = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new TryAcquireThread(semaphore, startSignal);
            threads[i].start();
        }
        startSignal.countDown();

        int permitsGranted = 0;
        for (final TryAcquireThread thread : threads) {
            thread.join();
            if (thread.acquired) {
                permitsGranted++;
            }
        }
        assertEquals(LIMIT, permitsGranted, "Wrong number of permits granted");
    }

    /**
     * Tests that tryAcquire() throws {@link IllegalStateException} after shutdown().
     */
    @Test
    void testTryAcquireAfterShutdown() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();
        assertThrows(IllegalStateException.class, semaphore::tryAcquire);
    }
}
