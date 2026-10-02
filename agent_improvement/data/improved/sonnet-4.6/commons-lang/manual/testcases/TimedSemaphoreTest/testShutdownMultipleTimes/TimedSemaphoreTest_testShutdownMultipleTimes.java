package org.apache.commons.lang3.concurrent;

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

public class TimedSemaphoreTest_testShutdownMultipleTimes extends AbstractLangTest {

    /** Time period used when constructing a TimedSemaphore under test. */
    private static final long PERIOD_MILLIS = 500;

    /** Time unit that matches PERIOD_MILLIS. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** Maximum number of acquire() calls allowed within one time period. */
    private static final int LIMIT = 10;

    /** Number of times shutdown() is called to verify idempotency. */
    private static final int SHUTDOWN_INVOCATION_COUNT = 10;

    /**
     * Registers a mock expectation that the executor will schedule the
     * semaphore's internal timer task exactly once and return the given future.
     */
    private void prepareStartTimer(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(),
                EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    /**
     * Verifies that calling shutdown() more than once is safe and idempotent:
     * the internal timer task must be cancelled exactly once, no matter how
     * many times shutdown() is invoked.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testShutdownMultipleTimes() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);

        // The timer starts on the first acquire(); the future must be cancelled exactly once on shutdown().
        prepareStartTimer(service, future);
        EasyMock.expect(Boolean.valueOf(future.cancel(false))).andReturn(Boolean.TRUE);
        EasyMock.replay(service, future);

        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, LIMIT);

        // Trigger timer start via the first acquire.
        semaphore.acquire();

        // Call shutdown() repeatedly; only the first call should have any effect.
        for (int i = 0; i < SHUTDOWN_INVOCATION_COUNT; i++) {
            semaphore.shutdown();
        }

        // Confirm that scheduleAtFixedRate and future.cancel(false) were each called exactly once.
        EasyMock.verify(service, future);
    }
}
