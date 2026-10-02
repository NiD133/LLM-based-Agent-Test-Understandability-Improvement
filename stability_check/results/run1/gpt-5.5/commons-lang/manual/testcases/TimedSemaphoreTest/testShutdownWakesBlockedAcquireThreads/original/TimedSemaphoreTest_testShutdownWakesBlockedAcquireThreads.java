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

public class TimedSemaphoreTest_testShutdownWakesBlockedAcquireThreads extends AbstractLangTest {

    /**
     * Constant for the time period.
     */
    private static final long PERIOD_MILLIS = 500;

    private static final Duration DURATION = Duration.ofMillis(PERIOD_MILLIS);

    /**
     * Constant for the time unit.
     */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /**
     * Constant for the default limit.
     */
    private static final int LIMIT = 10;

    /**
     * Prepares an executor service mock to expect the start of the timer.
     *
     * @param service the mock
     * @param future the future
     */
    private void prepareStartTimer(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
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
            // consume the only permit for this period.
            sem.acquire();
            final Thread blocker = new Thread(() -> {
                try {
                    // limit=1 already taken => blocks in wait().
                    sem.acquire();
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
            assertFalse(blocker.isAlive(), "TimedSemaphore.shutdown() failed to wake thread blocked in acquire(): blocker still alive in state=" + blocker.getState() + " 5s after shutdown(). Bug present (shutdown() does not call notifyAll()).");
        });
    }
}
