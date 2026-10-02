package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testShutdownWakesBlockedAcquireThreads extends AbstractLangTest {

    /**
     * Polls every 10 ms until {@code thread} reaches {@link Thread.State#WAITING} or the timeout elapses.
     */
    private void waitUntilThreadWaiting(final Thread thread, final Duration timeout) throws InterruptedException {
        final long deadline = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < deadline && thread.getState() != Thread.State.WAITING) {
            Thread.sleep(10);
        }
    }

    /**
     * Verifies that {@link TimedSemaphore#shutdown()} wakes threads blocked inside {@link TimedSemaphore#acquire()}.
     *
     * <p>The semaphore uses a 60-second period so the automatic {@code endOfPeriod()} reset never fires within
     * the test window. Therefore the only way a blocked thread can be unblocked is if {@code shutdown()}
     * calls {@code notifyAll()}. If it does not, the thread stays parked indefinitely and the test fails.</p>
     *
     * <p>Acceptable outcomes for the blocked thread after {@code shutdown()}:</p>
     * <ul>
     *   <li>Terminates normally after the wake-up, or</li>
     *   <li>Throws {@link IllegalStateException} when {@code acquire()} rechecks the shutdown flag on wake.</li>
     * </ul>
     */
    @Test
    public void testShutdownWakesBlockedAcquireThreads() {
        // Hard deadline prevents a regression from hanging the entire test suite.
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            // 60-second period: endOfPeriod() will NOT fire during the test,
            // so the only wake-up path is through shutdown() calling notifyAll().
            final TimedSemaphore semaphore = TimedSemaphore.builder()
                    .setPeriod(60)
                    .setTimeUnit(TimeUnit.SECONDS)
                    .setLimit(1)
                    .get();

            // Consume the only available permit so the next acquire() will block.
            semaphore.acquire();

            final Thread blockedThread = new Thread(() -> {
                try {
                    semaphore.acquire(); // blocks: permit already exhausted for this period
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (final IllegalStateException e) {
                    // Expected when shutdown() fires while acquire() is waiting:
                    // acquire() rechecks the shutdown flag and throws ISE.
                }
            }, "testShutdownWakesBlockedAcquireThreads-blocker");
            blockedThread.setDaemon(true);
            blockedThread.start();

            // Wait until the thread is parked in Object.wait() inside acquire() before
            // triggering shutdown, so there is no start-up race condition.
            waitUntilThreadWaiting(blockedThread, Duration.ofSeconds(2));

            semaphore.shutdown();

            // After shutdown() the blocker must wake and terminate within 5 seconds.
            // If shutdown() omits notifyAll(), the blocker stays parked for 60 s and this fails.
            blockedThread.join(5_000);
            assertFalse(blockedThread.isAlive(),
                    "shutdown() did not wake the thread blocked in acquire() — notifyAll() was not called");
        });
    }
}
