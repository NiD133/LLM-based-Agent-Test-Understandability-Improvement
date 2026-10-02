package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link TimedSemaphore#shutdown()} wakes up a thread that is
 * currently blocked inside {@link TimedSemaphore#acquire()}.
 *
 * <p>
 * The semaphore is deliberately created with a very long period (60 seconds) and a
 * limit of one permit. This guarantees that the periodic {@code endOfPeriod()} task
 * cannot fire during the test window, so the <em>only</em> way a blocked thread can
 * ever wake up is if {@code shutdown()} correctly calls {@code notifyAll()}:
 * </p>
 * <ul>
 * <li>Correct behavior: {@code shutdown()} calls {@code notifyAll()}; the blocked
 * thread wakes, re-checks the shutdown flag inside {@code acquire()}, throws
 * {@link IllegalStateException}, and terminates within milliseconds.</li>
 * <li>Buggy behavior: {@code shutdown()} sets the flag but never notifies waiters;
 * the blocked thread stays parked for the full 60-second period, long past the join
 * timeout below, and remains alive.</li>
 * </ul>
 */
public class TimedSemaphoreTest_testShutdownWakesBlockedAcquireThreads extends AbstractLangTest {

    /** Overall safety net: the framework preemptively interrupts a hung test. */
    private static final Duration TEST_TIMEOUT = Duration.ofSeconds(10);

    /** How long to wait for the blocker thread to actually reach WAITING state. */
    private static final Duration PARK_TIMEOUT = Duration.ofSeconds(2);

    /** How long to wait for the blocker thread to terminate after shutdown(). */
    private static final long JOIN_TIMEOUT_MILLIS = 5000;

    @Test
    public void testShutdownWakesBlockedAcquireThreads() {
        assertTimeoutPreemptively(TEST_TIMEOUT, () -> {
            // A 60s period ensures endOfPeriod() never fires during the test, so
            // notifyAll() from shutdown() is the only possible wake-up source.
            final TimedSemaphore semaphore = TimedSemaphore.builder()
                    .setPeriod(60)
                    .setTimeUnit(TimeUnit.SECONDS)
                    .setLimit(1)
                    .get();

            // Consume the single permit available for this period.
            semaphore.acquire();

            // This thread requests a second permit and therefore blocks in wait().
            final Thread blocker = new Thread(() -> {
                try {
                    semaphore.acquire();
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (final IllegalStateException e) {
                    // Expected correct-behavior outcome: the shutdown flag is
                    // re-checked after wake-up and this exception is thrown.
                }
            }, "testShutdownWakesBlockedAcquireThreads");
            blocker.setDaemon(true);
            blocker.start();

            // Wait until the blocker is genuinely parked in Object.wait() inside
            // acquire() before shutting down (avoids a race with shutdown()).
            final long parkDeadline = System.nanoTime() + PARK_TIMEOUT.toNanos();
            while (System.nanoTime() < parkDeadline && blocker.getState() != Thread.State.WAITING) {
                Thread.sleep(10);
            }

            semaphore.shutdown();

            blocker.join(JOIN_TIMEOUT_MILLIS);
            assertFalse(blocker.isAlive(),
                    "TimedSemaphore.shutdown() failed to wake thread blocked in acquire(): blocker still alive in state="
                            + blocker.getState()
                            + " 5s after shutdown(). Bug present (shutdown() does not call notifyAll()).");
        });
    }
}
