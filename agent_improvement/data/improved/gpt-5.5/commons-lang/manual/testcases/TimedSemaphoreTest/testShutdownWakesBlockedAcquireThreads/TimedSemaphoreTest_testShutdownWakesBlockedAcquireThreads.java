package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testShutdownWakesBlockedAcquireThreads extends AbstractLangTest {

    private static final String BLOCKED_THREAD_NAME = "testShutdownWakesBlockedAcquireThreads";

    /**
     * {@link TimedSemaphore#shutdown()} must wake threads blocked in
     * {@link TimedSemaphore#acquire()} so that they can observe the shutdown state
     * and terminate.
     */
    @Test
    public void testShutdownWakesBlockedAcquireThreads() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            final TimedSemaphore semaphore = createSemaphoreWhoseTimerCannotWakeTheBlockedThread();

            semaphore.acquire();
            final Thread blockedAcquireThread = startThreadBlockedOnAcquire(semaphore);
            waitUntilThreadIsParkedInAcquire(blockedAcquireThread);

            semaphore.shutdown();
            blockedAcquireThread.join(5000);

            assertFalse(blockedAcquireThread.isAlive(),
                "TimedSemaphore.shutdown() failed to wake thread blocked in acquire(): blocker still alive in state="
                    + blockedAcquireThread.getState() + " 5s after shutdown(). Bug present (shutdown() does not call notifyAll()).");
        });
    }

    private TimedSemaphore createSemaphoreWhoseTimerCannotWakeTheBlockedThread() {
        return TimedSemaphore.builder().setPeriod(60).setTimeUnit(TimeUnit.SECONDS).setLimit(1).get();
    }

    private Thread startThreadBlockedOnAcquire(final TimedSemaphore semaphore) {
        final Thread thread = new Thread(() -> {
            try {
                semaphore.acquire();
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (final IllegalStateException e) {
                // Expected after shutdown(): acquire() rechecks the shutdown flag.
            }
        }, BLOCKED_THREAD_NAME);
        thread.setDaemon(true);
        thread.start();
        return thread;
    }

    private void waitUntilThreadIsParkedInAcquire(final Thread thread) throws InterruptedException {
        final long parkDeadline = System.nanoTime() + Duration.ofSeconds(2).toNanos();
        while (System.nanoTime() < parkDeadline && thread.getState() != Thread.State.WAITING) {
            Thread.sleep(10);
        }
    }
}
