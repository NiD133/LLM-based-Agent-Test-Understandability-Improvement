package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testShutdownWakesBlockedAcquireThreads extends AbstractLangTest {

    /** A very long period so endOfPeriod() never fires during the test window. */
    private static final int LONG_PERIOD_SECONDS = 60;

    /** Limit of 1 permit per period, so the second acquire() will always block. */
    private static final int SINGLE_PERMIT = 1;

    /** How long to wait for the blocker to reach Thread.State.WAITING before calling shutdown(). */
    private static final Duration BLOCKER_PARK_TIMEOUT = Duration.ofSeconds(2);

    /** How long to wait for the blocker to terminate after shutdown(). */
    private static final long BLOCKER_JOIN_MILLIS = 5_000;

    /** Overall test timeout — prevents the JVM hanging if the bug is present. */
    private static final Duration TEST_TIMEOUT = Duration.ofSeconds(10);

    /**
     * Verifies that {@link TimedSemaphore#shutdown()} wakes threads blocked inside {@link TimedSemaphore#acquire()}.
     *
     * <p>Setup: a semaphore with limit=1 and a period of 60 s. The test thread consumes the only permit,
     * then a second thread calls {@code acquire()} and blocks in {@code Object.wait()}. Calling
     * {@code shutdown()} must notify blocked threads so the blocker either returns normally or throws
     * {@link IllegalStateException} and terminates promptly.</p>
     *
     * <p>The 60-second period ensures the periodic {@code endOfPeriod()} task cannot wake the blocker
     * within the test window; only an explicit {@code notifyAll()} inside {@code shutdown()} can do so.</p>
     */
    @Test
    public void testShutdownWakesBlockedAcquireThreads() {
        assertTimeoutPreemptively(TEST_TIMEOUT, () -> {
            final TimedSemaphore sem = TimedSemaphore.builder()
                    .setPeriod(LONG_PERIOD_SECONDS)
                    .setTimeUnit(TimeUnit.SECONDS)
                    .setLimit(SINGLE_PERMIT)
                    .get();

            // Consume the only permit for this period.
            sem.acquire();

            // Start a thread that will block in acquire() because the single permit is already taken.
            final Thread blocker = new Thread(() -> {
                try {
                    sem.acquire(); // blocks — limit already reached
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (final IllegalStateException e) {
                    // Expected when shutdown() is called while the thread is waiting.
                }
            }, "testShutdownWakesBlockedAcquireThreads");
            blocker.setDaemon(true);
            blocker.start();

            // Wait until the blocker is actually parked in Object.wait() before calling shutdown(),
            // so the shutdown notification is not lost due to a race.
            final long parkDeadline = System.nanoTime() + BLOCKER_PARK_TIMEOUT.toNanos();
            while (System.nanoTime() < parkDeadline && blocker.getState() != Thread.State.WAITING) {
                Thread.sleep(10);
            }

            // shutdown() must call notifyAll() so the blocked thread can observe the shutdown flag.
            sem.shutdown();

            blocker.join(BLOCKER_JOIN_MILLIS);
            assertFalse(blocker.isAlive(),
                    "TimedSemaphore.shutdown() failed to wake thread blocked in acquire(): "
                    + "blocker still alive in state=" + blocker.getState()
                    + " after " + BLOCKER_JOIN_MILLIS + " ms. "
                    + "Bug: shutdown() does not call notifyAll().");
        });
    }
}
