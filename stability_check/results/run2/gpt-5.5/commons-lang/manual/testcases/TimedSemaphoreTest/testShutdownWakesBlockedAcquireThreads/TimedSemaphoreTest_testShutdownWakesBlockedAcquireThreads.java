package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testShutdownWakesBlockedAcquireThreads extends AbstractLangTest {

    private static final int ONE_PERMIT = 1;
    private static final long SEMAPHORE_PERIOD_SECONDS = 60;
    private static final Duration TEST_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration WAIT_FOR_BLOCKER_TO_PARK = Duration.ofSeconds(2);
    private static final long BLOCKER_STATE_POLL_MILLIS = 10;
    private static final long BLOCKER_JOIN_MILLIS = 5000;
    private static final String BLOCKER_THREAD_NAME = "testShutdownWakesBlockedAcquireThreads";

    /**
     * TimedSemaphore.shutdown() must wake threads blocked in acquire().
     */
    @Test
    public void testShutdownWakesBlockedAcquireThreads() {
        assertTimeoutPreemptively(TEST_TIMEOUT, () -> {
            final TimedSemaphore semaphore = newSemaphoreWithLongPeriodAndSinglePermit();

            semaphore.acquire();
            final Thread blocker = startBlockedAcquireThread(semaphore);

            waitUntilThreadIsWaiting(blocker);
            semaphore.shutdown();

            blocker.join(BLOCKER_JOIN_MILLIS);
            assertFalse(blocker.isAlive(),
                    "TimedSemaphore.shutdown() failed to wake thread blocked in acquire(): blocker still alive in state=" + blocker.getState()
                            + " 5s after shutdown(). Bug present (shutdown() does not call notifyAll()).");
        });
    }

    private TimedSemaphore newSemaphoreWithLongPeriodAndSinglePermit() {
        return TimedSemaphore.builder().setPeriod(SEMAPHORE_PERIOD_SECONDS).setTimeUnit(TimeUnit.SECONDS).setLimit(ONE_PERMIT).get();
    }

    private Thread startBlockedAcquireThread(final TimedSemaphore semaphore) {
        final Thread blocker = new Thread(() -> {
            try {
                semaphore.acquire();
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (final IllegalStateException e) {
                // Expected after shutdown wakes the blocked acquire().
            }
        }, BLOCKER_THREAD_NAME);
        blocker.setDaemon(true);
        blocker.start();
        return blocker;
    }

    private void waitUntilThreadIsWaiting(final Thread blocker) throws InterruptedException {
        final long parkDeadline = System.nanoTime() + WAIT_FOR_BLOCKER_TO_PARK.toNanos();
        while (System.nanoTime() < parkDeadline && blocker.getState() != Thread.State.WAITING) {
            Thread.sleep(BLOCKER_STATE_POLL_MILLIS);
        }
    }
}
