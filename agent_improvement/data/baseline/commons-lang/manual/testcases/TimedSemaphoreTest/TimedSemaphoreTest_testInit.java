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

public class TimedSemaphoreTest_testInit extends AbstractLangTest {

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
     * Tests creating a new instance.
     */
    @Test
    void testInit() {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        EasyMock.replay(service);
        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);
        EasyMock.verify(service);
        assertEquals(service, semaphore.getExecutorService(), "Wrong service");
        assertEquals(PERIOD_MILLIS, semaphore.getPeriod(), "Wrong period");
        assertEquals(UNIT, semaphore.getUnit(), "Wrong unit");
        assertEquals(0, semaphore.getLastAcquiresPerPeriod(), "Statistic available");
        assertEquals(0.0, semaphore.getAverageCallsPerPeriod(), .05, "Average available");
        assertFalse(semaphore.isShutdown(), "Already shutdown");
        assertEquals(LIMIT, semaphore.getLimit(), "Wrong limit");
    }
}
