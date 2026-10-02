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

public class TimedSemaphoreTest_testAcquireMultipleThreads extends AbstractLangTest {

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
     * Tests the acquire() method if more threads are involved than the limit.
     * This method starts a number of threads that all invoke the semaphore. The
     * semaphore's limit is set to 1, so in each period only a single thread can
     * acquire the semaphore.
     *
     * @throws InterruptedException so we don't have to catch it
     */
    @Test
    void testAcquireMultipleThreads() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        prepareStartTimer(service, future);
        EasyMock.replay(service, future);
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, UNIT, 1);
        semaphore.latch = new CountDownLatch(1);
        final int count = 10;
        final SemaphoreThread[] threads = new SemaphoreThread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new SemaphoreThread(semaphore, null, 1, 0);
            threads[i].start();
        }
        for (int i = 0; i < count; i++) {
            semaphore.latch.await();
            assertEquals(1, semaphore.getAcquireCount(), "Wrong count");
            semaphore.latch = new CountDownLatch(1);
            semaphore.endOfPeriod();
            assertEquals(1, semaphore.getLastAcquiresPerPeriod(), "Wrong acquire count");
        }
        for (int i = 0; i < count; i++) {
            threads[i].join();
        }
        EasyMock.verify(service, future);
    }
}
