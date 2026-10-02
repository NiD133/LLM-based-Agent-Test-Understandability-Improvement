package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testShutdownWakesBlockedAcquireThreads extends AbstractLangTest {

    private static final Duration TEST_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration WAITING_STATE_TIMEOUT = Duration.ofSeconds(2);
    private static final long WAITING_STATE_POLL_MILLIS = 10;
    private static final long BLOCKED_THREAD_JOIN_MILLIS = 5000;

    @Test
    public void testShutdownWakesBlockedAcquireThreads() {
        assertTimeoutPreemptively(TEST_TIMEOUT, () -> {
            final TimedSemaphore semaphore = TimedSemaphore.builder().setPeriod(60).setTimeUnit(TimeUnit.SECONDS).setLimit(1).get();

            semaphore.acquire();

            final Thread blockedAcquireThread = newBlockedAcquireThread(semaphore);
            blockedAcquireThread.start();

            waitUntilThreadIsWaiting(blockedAcquireThread);

            semaphore.shutdown();

            blockedAcquireThread.join(BLOCKED_THREAD_JOIN_MILLIS);
            assertFalse(blockedAcquireThread.isAlive(),
                    "TimedSemaphore.shutdown() failed to wake thread blocked in acquire(): blocker still alive in state="
                            + blockedAcquireThread.getState() + " 5s after shutdown(). Bug present (shutdown() does not call notifyAll()).");
        });
    }

    private Thread newBlockedAcquireThread(final TimedSemaphore semaphore) {
        final Thread thread = new Thread(() -> {
            try {
                semaphore.acquire();
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (final IllegalStateException e) {
                // Expected after shutdown() wakes the thread and acquire() re-checks the shutdown state.
            }
        }, "testShutdownWakesBlockedAcquireThreads");
        thread.setDaemon(true);
        return thread;
    }

    private void waitUntilThreadIsWaiting(final Thread thread) throws InterruptedException {
        final long deadline = System.nanoTime() + WAITING_STATE_TIMEOUT.toNanos();
        while (System.nanoTime() < deadline && thread.getState() != Thread.State.WAITING) {
            Thread.sleep(WAITING_STATE_POLL_MILLIS);
        }
    }
}
