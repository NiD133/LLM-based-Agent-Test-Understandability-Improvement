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
     * A worker thread that repeatedly calls {@link TimedSemaphore#acquire()} on
     * the semaphore under test. It optionally counts down a latch for the first
     * few acquisitions so the main test thread can synchronize with its progress.
     */
    private static final class SemaphoreThread extends Thread {

        /** The semaphore to acquire from. */
        private final TimedSemaphore semaphore;

        /** Latch used to signal progress back to the main thread (may be {@code null}). */
        private final CountDownLatch latch;

        /** How many times {@code acquire()} should be called. */
        private final int acquireCount;

        /** For how many of the {@code acquire()} calls the latch should be counted down. */
        private final int latchCountDowns;

        SemaphoreThread(final TimedSemaphore semaphore, final CountDownLatch latch,
                final int acquireCount, final int latchCountDowns) {
            this.semaphore = semaphore;
            this.latch = latch;
            this.acquireCount = acquireCount;
            this.latchCountDowns = latchCountDowns;
        }

        /**
         * Calls {@code acquire()} the configured number of times, counting down
         * the latch for the first {@link #latchCountDowns} invocations.
         */
        @Override
        public void run() {
            try {
                for (int i = 0; i < acquireCount; i++) {
                    semaphore.acquire();

                    if (i < latchCountDowns) {
                        latch.countDown();
                    }
                }
            } catch (final InterruptedException iex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * A {@link TimedSemaphore} subclass that makes the class easier to test by
     * exposing a controllable timer future, a synchronization latch, and a count
     * of how often {@code endOfPeriod()} has been invoked.
     */
    private static final class TimedSemaphoreTestImpl extends TimedSemaphore {

        /** When set, {@link #startTimer()} returns this mock instead of scheduling a real task. */
        ScheduledFuture<?> schedFuture;

        /** When set, it is counted down on every {@code acquire()}, letting the main thread synchronize. */
        volatile CountDownLatch latch;

        /** Number of times {@link #endOfPeriod()} has been invoked. */
        private int periodEnds;

        TimedSemaphoreTestImpl(final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(timePeriod, timeUnit, limit);
        }

        TimedSemaphoreTestImpl(final ScheduledExecutorService service, final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(service, timePeriod, timeUnit, limit);
        }

        /**
         * Acquires a permit and, if a latch is set, counts it down so the main
         * thread can observe that the acquisition happened.
         *
         * @throws InterruptedException because it is declared that way in TimedSemaphore
         */
        @Override
        public synchronized void acquire() throws InterruptedException {
            super.acquire();
            if (latch != null) {
                latch.countDown();
            }
        }

        /**
         * Counts the number of invocations.
         */
        @Override
        protected synchronized void endOfPeriod() {
            super.endOfPeriod();
            periodEnds++;
        }

        /**
         * Returns the number of invocations of the endOfPeriod() method.
         *
         * @return the endOfPeriod() invocations
         */
        int getPeriodEnds() {
            synchronized (this) {
                return periodEnds;
            }
        }

        /**
         * Returns the controllable mock future when one is configured, otherwise
         * delegates to the real timer.
         */
        @Override
        protected ScheduledFuture<?> startTimer() {
            return schedFuture != null ? schedFuture : super.startTimer();
        }
    }

    /**
     * A worker thread that waits on a shared latch and then performs a single
     * {@link TimedSemaphore#tryAcquire()}, recording whether a permit was granted.
     */
    private static final class TryAcquireThread extends Thread {

        /** The semaphore to try to acquire from. */
        private final TimedSemaphore semaphore;

        /** Latch that releases all worker threads at the same time. */
        private final CountDownLatch latch;

        /** Whether this thread managed to acquire a permit. */
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
                // ignore
            }
        }
    }

    /** The time period used in tests. */
    private static final long PERIOD_MILLIS = 500;

    /** The time period as a {@link Duration}. */
    private static final Duration DURATION = Duration.ofMillis(PERIOD_MILLIS);

    /** The time unit used in tests. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** The default permit limit used in tests. */
    private static final int LIMIT = 10;

    /**
     * Configures the executor service mock so that it expects the periodic timer
     * task to be scheduled at the test period and returns the supplied future.
     *
     * @param service the executor service mock
     * @param future the future the scheduling call should return
     */
    private void prepareStartTimer(final ScheduledExecutorService service,
            final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Tests {@code acquire()} when a limit is set: once the limit is reached the
     * calling thread blocks, and it is only released when the period ends.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testAcquireLimit() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);
        final int count = 10;
        final CountDownLatch latch = new CountDownLatch(count - 1);
        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, 1);
        final SemaphoreThread semaphoreThread = new SemaphoreThread(semaphore, latch, count, count - 1);
        semaphore.setLimit(count - 1);
        // Start a thread that calls acquire() count times. It blocks once the
        // limit (count - 1) is reached.
        semaphoreThread.start();
        latch.await();
        // Now the semaphore's limit is reached and the thread is blocked.
        assertEquals(count - 1, semaphore.getAcquireCount(), "Wrong semaphore count");
        // Ending the period wakes the thread so it can perform its final acquire().
        semaphore.endOfPeriod();
        semaphoreThread.join();
        assertEquals(1, semaphore.getAcquireCount(), "Wrong semaphore count (2)");
        assertEquals(count - 1, semaphore.getLastAcquiresPerPeriod(), "Wrong acquire() count");
        EasyMock.verify(service, future);
    }

    /**
     * Tests a large number of acquisitions spanning multiple periods. With a very
     * short period and a background thread acquiring many times, at least one end
     * of period must be reached while the thread runs.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testAcquireMultiplePeriods() throws InterruptedException {
        final int count = 1000;
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(PERIOD_MILLIS / 10, TimeUnit.MILLISECONDS, 1);
        semaphore.setLimit(count / 4);
        final CountDownLatch latch = new CountDownLatch(count);
        final SemaphoreThread semaphoreThread = new SemaphoreThread(semaphore, latch, count, count);
        semaphoreThread.start();
        latch.await();
        semaphore.shutdown();
        assertTrue(semaphore.getPeriodEnds() > 0, "End of period not reached");
    }

    /**
     * Tests {@code acquire()} when more threads compete than the limit allows.
     * With a limit of 1, only a single thread can acquire the semaphore per
     * period; the others must wait until the next period.
     *
     * @throws InterruptedException so we don't have to catch it
     */
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
        // Each iteration lets exactly one thread through, then ends the period to
        // release the next one.
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

    /**
     * Tests {@code acquire()} when no limit is set: a thread acquiring a large
     * number of times must never block, even though the period never ends.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testAcquireNoLimit() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, TimedSemaphore.NO_LIMIT);
        final int count = 1000;
        final CountDownLatch latch = new CountDownLatch(count);
        final SemaphoreThread semaphoreThread = new SemaphoreThread(semaphore, latch, count, count);
        semaphoreThread.start();
        latch.await();
        EasyMock.verify(service, future);
    }

    /**
     * Tests that the number of available (non-blocking) permits can be queried,
     * decreasing with each acquire and resetting at the end of a period.
     *
     * @throws InterruptedException so we don't have to catch it
     */
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

    /**
     * Tests the statistics methods, in particular the running average number of
     * acquire calls per period.
     *
     * @throws InterruptedException so we don't have to catch it
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
        // One period with one acquire => average 1.0.
        assertEquals(1.0, semaphore.getAverageCallsPerPeriod(), .005, "Wrong average (1)");
        semaphore.acquire();
        semaphore.acquire();
        semaphore.endOfPeriod();
        // Two periods with 1 and 2 acquires => average 1.5.
        assertEquals(1.5, semaphore.getAverageCallsPerPeriod(), .005, "Wrong average (2)");
        EasyMock.verify(service, future);
    }

    /**
     * Tests that a newly created instance exposes the construction parameters and
     * starts in a sane, not-yet-shutdown state.
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
     * Tests that a default executor service is created (and configured correctly)
     * when none is provided to the constructor.
     */
    @Test
    void testInitDefaultService() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        final ScheduledThreadPoolExecutor exec = (ScheduledThreadPoolExecutor) semaphore.getExecutorService();
        assertFalse(exec.getContinueExistingPeriodicTasksAfterShutdownPolicy(), "Wrong periodic task policy");
        assertFalse(exec.getExecuteExistingDelayedTasksAfterShutdownPolicy(), "Wrong delayed task policy");
        assertFalse(exec.isShutdown(), "Already shutdown");
        semaphore.shutdown();
    }

    /**
     * Tests that constructing an instance with a non-positive period is rejected.
     */
    @Test
    void testInitInvalidPeriod() {
        assertIllegalArgumentException(() -> new TimedSemaphore(0L, UNIT, LIMIT));
    }

    /**
     * Tests that calling {@code acquire()} after {@code shutdown()} throws.
     */
    @Test
    void testPassAfterShutdown() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();
        assertThrows(IllegalStateException.class, semaphore::acquire);
    }

    /**
     * Tests that {@code shutdown()} is idempotent: invoking it repeatedly only
     * cancels the timer task once.
     *
     * @throws InterruptedException so we don't have to catch it
     */
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

    /**
     * Tests {@code shutdown()} when the executor is owned by the semaphore: both
     * the semaphore and its executor must be shut down.
     */
    @Test
    void testShutdownOwnExecutor() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();
        assertTrue(semaphore.isShutdown(), "Not shutdown");
        assertTrue(semaphore.getExecutorService().isShutdown(), "Executor not shutdown");
    }

    /**
     * Tests {@code shutdown()} for a shared executor when no timer task has been
     * started yet. This should mark the semaphore shut down without touching the
     * executor.
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
     * Tests {@code shutdown()} for a shared executor after the timer task has been
     * started: the task must be canceled but the shared executor left running.
     *
     * @throws InterruptedException so we don't have to catch it
     */
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
     * TimedSemaphore.shutdown() must wake threads blocked in acquire().
     *
     * <p>
     * Pre-patch ({@code shutdown()} sets the flag but does not call {@code notifyAll()}): a thread parked in {@code wait()} inside {@code acquire()} stays
     * parked indefinitely — until the periodic {@code endOfPeriod()} task fires, which never happens here because we deliberately use a long period (60 s).
     * </p>
     *
     * <p>
     * Post-patch: {@code shutdown()} calls {@code notifyAll()} after setting the flag, and {@code acquire()} re-checks the flag on wake to throw
     * {@link IllegalStateException}.
     * </p>
     *
     * <p>
     * Differences from the previous (vacuous) version of this PoC:
     * </p>
     * <ul>
     * <li>Uses period = 60 s (was 1 s). With a 1-second period, the periodic {@code endOfPeriod()} task wakes the blocker on the next tick, hiding the
     * bug.</li>
     * <li>Asserts {@code !blocker.isAlive()} after the join. {@code Thread.join(timeout)} returns silently after the timeout regardless of whether the thread
     * terminated, so the previous assertion ({@code assertTimeout(2s, () -> blocker.join(1500))}) was satisfied even when the blocker was still parked.</li>
     * <li>Uses {@code assertTimeoutPreemptively} so the test framework forcibly interrupts a hanging test rather than hanging the JVM.</li>
     * <li>Waits for the blocker to actually reach {@code Thread.State.WAITING} before calling {@code shutdown()}, removing the {@code Thread.sleep(100)}
     * race.</li>
     * </ul>
     */
    @Test
    public void testShutdownWakesBlockedAcquireThreads() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            // Period of 60s ensures endOfPeriod() does NOT fire during the test
            // window. The only way the blocker can wake is via shutdown() calling
            // notifyAll().
            final TimedSemaphore sem = TimedSemaphore.builder().setPeriod(60).setTimeUnit(TimeUnit.SECONDS).setLimit(1).get();
            sem.acquire(); // consume the only permit for this period.
            final Thread blocker = new Thread(() -> {
                try {
                    sem.acquire(); // limit=1 already taken => blocks in wait().
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (final IllegalStateException e) {
                    // Acceptable post-patch outcome: re-check of shutdown flag throws.
                }
            }, "testShutdownWakesBlockedAcquireThreads");
            blocker.setDaemon(true);
            blocker.start();
            // Wait until blocker is parked in Object.wait() inside acquire().
            final long parkDeadline = System.nanoTime() + Duration.ofSeconds(2).toNanos();
            while (System.nanoTime() < parkDeadline && blocker.getState() != Thread.State.WAITING) {
                Thread.sleep(10);
            }
            sem.shutdown();
            // At HEAD (patched): blocker wakes from notifyAll(), re-checks flag,
            // throws ISE, and terminates within milliseconds.
            // At baseline: blocker stays in WAITING for 60s — well past this join.
            blocker.join(5000);
            assertFalse(blocker.isAlive(), "TimedSemaphore.shutdown() failed to wake thread blocked in acquire(): blocker still alive in state="
                    + blocker.getState() + " 5s after shutdown(). Bug present (shutdown() does not call notifyAll()).");
        });
    }

    /**
     * Tests that starting the timer schedules a real periodic task that
     * eventually triggers {@code endOfPeriod()}.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testStartTimer() throws InterruptedException {
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(PERIOD_MILLIS, UNIT, LIMIT);
        final ScheduledFuture<?> future = semaphore.startTimer();
        assertNotNull(future, "No future returned");
        ThreadUtils.sleepQuietly(DURATION);
        // Poll until endOfPeriod() fires, giving up after a bounded number of tries.
        final int trials = 10;
        int count = 0;
        do {
            Thread.sleep(PERIOD_MILLIS);
            assertFalse(count++ > trials, "endOfPeriod() not called!");
        } while (semaphore.getPeriodEnds() <= 0);
        semaphore.shutdown();
    }

    /**
     * Tests {@code tryAcquire()}: when many threads contend at once, exactly
     * {@link #LIMIT} of them obtain a permit and the rest are refused.
     */
    @Test
    void testTryAcquire() throws InterruptedException {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, TimeUnit.SECONDS, LIMIT);
        final TryAcquireThread[] threads = new TryAcquireThread[3 * LIMIT];
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new TryAcquireThread(semaphore, latch);
            threads[i].start();
        }
        // Release all threads simultaneously so they contend for permits at once.
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

    /**
     * Tests that calling {@code tryAcquire()} after {@code shutdown()} throws.
     */
    @Test
    void testTryAcquireAfterShutdown() {
        final TimedSemaphore semaphore = new TimedSemaphore(PERIOD_MILLIS, UNIT, LIMIT);
        semaphore.shutdown();
        assertThrows(IllegalStateException.class, semaphore::tryAcquire);
    }
}
