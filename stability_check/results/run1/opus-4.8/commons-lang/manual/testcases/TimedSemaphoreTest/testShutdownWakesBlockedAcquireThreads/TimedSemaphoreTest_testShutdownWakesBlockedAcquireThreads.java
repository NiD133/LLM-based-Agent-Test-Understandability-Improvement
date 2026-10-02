package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link TimedSemaphore#shutdown()} wakes up any thread that is
 * currently blocked inside {@link TimedSemaphore#acquire()}.
 *
 * <p>
 * The semaphore is configured with a deliberately long period (60&nbsp;seconds) and a
 * limit of a single permit. After the only permit is consumed, a second thread that
 * calls {@code acquire()} is guaranteed to park in {@code Object.wait()}. Because the
 * period is so long, the periodic {@code endOfPeriod()} task cannot fire during the
 * test, so the <em>only</em> way the blocked thread can wake is if {@code shutdown()}
 * calls {@code notifyAll()}.
 * </p>
 *
 * <ul>
 * <li><b>Correct behaviour:</b> {@code shutdown()} calls {@code notifyAll()};
 * {@code acquire()} re-checks the shutdown flag on wake and throws
 * {@link IllegalStateException}, so the blocked thread terminates promptly.</li>
 * <li><b>Bug present:</b> {@code shutdown()} sets the flag but never notifies, so the
 * blocked thread stays parked for the full 60-second period and is still alive when the
 * test checks.</li>
 * </ul>
 */
public class TimedSemaphoreTest_testShutdownWakesBlockedAcquireThreads extends AbstractLangTest {

    /** A period long enough that {@code endOfPeriod()} never fires during the test. */
    private static final long LONG_PERIOD = 60;

    /** Upper bound for the whole test; the framework interrupts it if exceeded. */
    private static final Duration TEST_TIMEOUT = Duration.ofSeconds(10);

    /** How long to wait for the blocker thread to actually park in {@code wait()}. */
    private static final Duration PARK_TIMEOUT = Duration.ofSeconds(2);

    /** How long to wait for the blocker thread to terminate after {@code shutdown()}. */
    private static final long JOIN_TIMEOUT_MILLIS = 5000;

    /**
     * Creates a thread that calls {@code acquire()} on an already-exhausted semaphore and
     * therefore blocks. The thread swallows the {@link IllegalStateException} thrown once
     * {@code shutdown()} wakes it, which is the expected outcome of correct behaviour.
     */
    private Thread newBlockingAcquireThread(final TimedSemaphore semaphore) {
        final Thread blocker = new Thread(() -> {
            try {
                // The single permit is already taken, so this call blocks in wait().
                semaphore.acquire();
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (final IllegalStateException e) {
                // Expected once shutdown() wakes us and acquire() re-checks the flag.
            }
        }, "testShutdownWakesBlockedAcquireThreads");
        blocker.setDaemon(true);
        return blocker;
    }

    /**
     * Busy-waits (up to {@link #PARK_TIMEOUT}) until the given thread reaches
     * {@link Thread.State#WAITING}, i.e. it is parked inside {@code acquire()}'s
     * {@code wait()} call. This removes any timing race before calling {@code shutdown()}.
     */
    private void waitUntilParked(final Thread thread) throws InterruptedException {
        final long deadlineNanos = System.nanoTime() + PARK_TIMEOUT.toNanos();
        while (System.nanoTime() < deadlineNanos && thread.getState() != Thread.State.WAITING) {
            Thread.sleep(10);
        }
    }

    @Test
    public void testShutdownWakesBlockedAcquireThreads() {
        assertTimeoutPreemptively(TEST_TIMEOUT, () -> {
            final TimedSemaphore semaphore = TimedSemaphore.builder()
                    .setPeriod(LONG_PERIOD)
                    .setTimeUnit(TimeUnit.SECONDS)
                    .setLimit(1)
                    .get();

            // Consume the only permit available for this period.
            semaphore.acquire();

            // Start a second thread that will block trying to acquire the exhausted permit.
            final Thread blocker = newBlockingAcquireThread(semaphore);
            blocker.start();
            waitUntilParked(blocker);

            // shutdown() must call notifyAll(); otherwise the blocker parks for 60s.
            semaphore.shutdown();

            // With correct behaviour the blocker wakes, throws ISE, and terminates quickly.
            blocker.join(JOIN_TIMEOUT_MILLIS);
            assertFalse(blocker.isAlive(),
                    "TimedSemaphore.shutdown() failed to wake thread blocked in acquire(): blocker still alive in state="
                            + blocker.getState()
                            + " 5s after shutdown(). Bug present (shutdown() does not call notifyAll()).");
        });
    }
}
