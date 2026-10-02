package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link TimedSemaphore#shutdown()} wakes threads that are blocked inside {@link TimedSemaphore#acquire()}.
 */
public class TimedSemaphoreTest_testShutdownWakesBlockedAcquireThreads extends AbstractLangTest {

    /**
     * A 60-second period ensures the periodic {@code endOfPeriod()} task never fires during the test.
     * This means the only mechanism that can unblock a waiting thread is {@code shutdown()} calling {@code notifyAll()}.
     * If {@code shutdown()} omits the {@code notifyAll()}, the blocked thread stays parked for 60 s and the test fails.
     */
    @Test
    public void testShutdownWakesBlockedAcquireThreads() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            // Arrange: semaphore with limit=1 and a very long period so endOfPeriod() never fires in this test
            final TimedSemaphore sem = TimedSemaphore.builder()
                    .setPeriod(60)
                    .setTimeUnit(TimeUnit.SECONDS)
                    .setLimit(1)
                    .get();

            // Consume the only available permit so the next acquire() must block
            sem.acquire();

            // Start a thread that tries to acquire a second permit and will block in wait()
            final Thread blocker = new Thread(() -> {
                try {
                    sem.acquire();
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (final IllegalStateException e) {
                    // Expected post-shutdown outcome: acquire() re-checks the shutdown flag and throws
                }
            }, "testShutdownWakesBlockedAcquireThreads");
            blocker.setDaemon(true);
            blocker.start();

            // Wait until the blocker has actually reached Object.wait() inside acquire()
            final long parkDeadline = System.nanoTime() + Duration.ofSeconds(2).toNanos();
            while (System.nanoTime() < parkDeadline && blocker.getState() != Thread.State.WAITING) {
                Thread.sleep(10);
            }

            // Act: shut down the semaphore — correct implementation calls notifyAll() to wake blockers
            sem.shutdown();

            // Assert: the blocker must terminate promptly after being woken by shutdown()
            blocker.join(5000);
            assertFalse(blocker.isAlive(),
                    "TimedSemaphore.shutdown() failed to wake thread blocked in acquire(): "
                    + "blocker still alive in state=" + blocker.getState()
                    + " 5s after shutdown(). Bug present (shutdown() does not call notifyAll()).");
        });
    }
}
