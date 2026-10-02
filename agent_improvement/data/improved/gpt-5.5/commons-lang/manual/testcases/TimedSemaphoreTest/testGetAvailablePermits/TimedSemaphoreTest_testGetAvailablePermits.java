package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testGetAvailablePermits extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final TimeUnit PERIOD_UNIT = TimeUnit.MILLISECONDS;
    private static final int PERMIT_LIMIT = 10;

    private void expectTimerStart(final ScheduledExecutorService service, final ScheduledFuture<?> future) {
        service.scheduleAtFixedRate((Runnable) EasyMock.anyObject(), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_MILLIS), EasyMock.eq(PERIOD_UNIT));
        EasyMock.expectLastCall().andReturn(future);
    }

    private void assertPermitsBeforeEachAcquire(final TimedSemaphore semaphore) throws InterruptedException {
        for (int acquiredPermits = 0; acquiredPermits < PERMIT_LIMIT; acquiredPermits++) {
            assertEquals(PERMIT_LIMIT - acquiredPermits, semaphore.getAvailablePermits(), "Wrong available count at " + acquiredPermits);
            semaphore.acquire();
        }
    }

    @Test
    void testGetAvailablePermits() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> future = EasyMock.createMock(ScheduledFuture.class);
        expectTimerStart(service, future);
        EasyMock.replay(service, future);

        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, PERIOD_UNIT, PERMIT_LIMIT);
        assertPermitsBeforeEachAcquire(semaphore);

        semaphore.endOfPeriod();
        assertEquals(PERMIT_LIMIT, semaphore.getAvailablePermits(), "Wrong available count in new period");
        EasyMock.verify(service, future);
    }
}
