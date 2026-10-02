package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link TimedSemaphore#shutdown()} wakes up a thread that is currently blocked inside
 * {@link TimedSemaphore#acquire()}.
 *
 * <p>
 * <strong>What the test pins down:</strong> {@code shutdown()} must call {@code notifyAll()} after setting its
 * shutdown flag. On wake-up, the blocked {@code acquire()} call re-checks the flag and throws
 * {@link IllegalStateException}, so the blocked thread terminates promptly.
 * </p>
 *
 * <p>
 * <strong>Why a 60-second period:</strong> a {@link TimedSemaphore} also releases blocked threads when its periodic
 * {@code endOfPeriod()} task fires. To prove that {@code shutdown()} itself does the waking, the period is set far
 * longer (60 s) than the test window, so {@code endOfPeriod()} cannot fire and mask a missing {@code notifyAll()}.
 * Without the fix, the blocked thread would stay parked for the full 60 s and the assertion below would fail.
 * </p>
 */
public class TimedSemaphoreTest_testShutdownWakesBlockedAcquireThreads extends AbstractLangTest {

    /** Period long enough that the periodic reset task never fires during the test. */
    private static final long LONG_PERIOD_SECONDS = 60;

    /** Upper bound for waiting until the blocker thread parks in {@code wait()}. */
    private static final Duration PARK_WAIT_TIMEOUT = Duration.ofSeconds(2);

    /** How long to wait for the blocker to terminate after {@code shutdown()}. */
    private static final long JOIN_TIMEOUT_MILLIS = 5000;

    /** Hard cap on the whole test so a regression hangs the test rather than the JVM. */
    private static final Duration TEST_TIMEOUT = Duration.ofSeconds(10);

    @Test
    public void testShutdownWakesBlockedAcquireThreads() {
        assertTimeoutPreemptively(TEST_TIMEOUT, () -> {
            // A single-permit semaphore whose period is too long to ever reset during the test,
            // so shutdown() is the only thing that can wake a blocked acquirer.
            final TimedSemaphore semaphore = TimedSemaphore.builder()
                    .setPeriod(LONG_PERIOD_SECONDS)
                    .setTimeUnit(TimeUnit.SECONDS)
                    .setLimit(1)
                    .get();

            // Consume the only permit available in this period.
            semaphore.acquire();

            // This thread will block in acquire() because the single permit is already taken.
            final Thread blocker = new Thread(() -> {
                try {
                    semaphore.acquire();
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (final IllegalStateException e) {
                    // Expected once shutdown() wakes us: acquire() re-checks the flag and throws.
                }
            }, "testShutdownWakesBlockedAcquireThreads");
            blocker.setDaemon(true);
            blocker.start();

            // Wait until the blocker is actually parked in Object.wait() inside acquire().
            // This replaces a fixed sleep and removes the race that one could introduce.
            waitUntilWaiting(blocker);

            semaphore.shutdown();

            // If shutdown() correctly calls notifyAll(), the blocker wakes, throws ISE, and dies
            // within milliseconds. Without it, the blocker stays WAITING far past this join.
            blocker.join(JOIN_TIMEOUT_MILLIS);

            assertFalse(blocker.isAlive(),
                    "TimedSemaphore.shutdown() failed to wake thread blocked in acquire(): blocker still alive in state="
                            + blocker.getState() + " " + (JOIN_TIMEOUT_MILLIS / 1000)
                            + "s after shutdown(). Bug present (shutdown() does not call notifyAll()).");
        });
    }

    /**
     * Spins until the given thread reaches {@link Thread.State#WAITING} or {@link #PARK_WAIT_TIMEOUT} elapses.
     *
     * @param thread the thread expected to park inside {@code acquire()}.
     * @throws InterruptedException if interrupted while polling.
     */
    private void waitUntilWaiting(final Thread thread) throws InterruptedException {
        final long deadline = System.nanoTime() + PARK_WAIT_TIMEOUT.toNanos();
        while (System.nanoTime() < deadline && thread.getState() != Thread.State.WAITING) {
            Thread.sleep(10);
        }
    }
}
