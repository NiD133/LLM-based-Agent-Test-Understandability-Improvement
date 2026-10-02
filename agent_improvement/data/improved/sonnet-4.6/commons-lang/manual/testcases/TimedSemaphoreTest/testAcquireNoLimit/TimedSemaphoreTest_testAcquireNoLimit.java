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

public class TimedSemaphoreTest_testAcquireNoLimit extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;

    private static final Duration DURATION = Duration.ofMillis(PERIOD_MILLIS);

    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    private static final int LIMIT = 10;

    /**
     * Registers the expectation that the executor will schedule a periodic timer task
     * starting at {@code PERIOD_MILLIS} and repeating at the same interval.
     */
    private void prepareStartTimer(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Verifies that {@code acquire()} never blocks when the semaphore limit is set to
     * {@link TimedSemaphore#NO_LIMIT}.
     *
     * <p>When NO_LIMIT is active the semaphore skips the per-period quota check entirely,
     * so a thread issuing many consecutive acquires should complete all of them without
     * waiting for the period to roll over.
     *
     * @throws InterruptedException if the test thread is unexpectedly interrupted
     */
    @Test
    void testAcquireNoLimit() throws InterruptedException {
        // Arrange: use a mock executor so the timer never fires; the thread must still
        // complete all acquires because NO_LIMIT bypasses the blocking check entirely.
        final ScheduledExecutorService mockExecutor = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> mockFuture = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(mockExecutor, mockFuture);
        EasyMock.replay(mockExecutor, mockFuture);

        final TimedSemaphoreTest.TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTest.TimedSemaphoreTestImpl(mockExecutor, PERIOD_MILLIS, UNIT, TimedSemaphore.NO_LIMIT);

        // Each acquire() call counts down the latch; the main thread blocks on latch.await()
        // until all acquires have been issued, confirming none of them blocked.
        final int acquireCallCount = 1000;
        final CountDownLatch allAcquiresDone = new CountDownLatch(acquireCallCount);
        final TimedSemaphoreTest.SemaphoreThread semaphoreThread = new TimedSemaphoreTest.SemaphoreThread(semaphore, allAcquiresDone, acquireCallCount, acquireCallCount);

        // Act
        semaphoreThread.start();
        allAcquiresDone.await();

        // Assert: verify the mock executor was used exactly as expected (timer was started once)
        EasyMock.verify(mockExecutor, mockFuture);
    }
}
