package org.apache.commons.lang3.concurrent;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testShutdownMultipleTimes extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final TimeUnit PERIOD_UNIT = TimeUnit.MILLISECONDS;
    private static final int LIMIT = 10;
    private static final int SHUTDOWN_INVOCATIONS = 10;

    private static final class TimedSemaphoreTestImpl extends TimedSemaphore {

        TimedSemaphoreTestImpl(final ScheduledExecutorService service, final long period, final TimeUnit unit, final int limit) {
            super(service, period, unit, limit);
        }
    }

    private void expectTimerStart(final ScheduledExecutorService service, final ScheduledFuture<?> timerTask) {
        service.scheduleAtFixedRate(
                (Runnable) EasyMock.anyObject(),
                EasyMock.eq(PERIOD_MILLIS),
                EasyMock.eq(PERIOD_MILLIS),
                EasyMock.eq(PERIOD_UNIT));
        EasyMock.expectLastCall().andReturn(timerTask);
    }

    /**
     * Verifies that repeated shutdown calls are idempotent: only the first call cancels the scheduled timer task.
     *
     * @throws InterruptedException if acquiring the semaphore is interrupted
     */
    @Test
    void testShutdownMultipleTimes() throws InterruptedException {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        final ScheduledFuture<?> timerTask = EasyMock.createMock(ScheduledFuture.class);

        expectTimerStart(service, timerTask);
        EasyMock.expect(Boolean.valueOf(timerTask.cancel(false))).andReturn(Boolean.TRUE);
        EasyMock.replay(service, timerTask);

        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(service, PERIOD_MILLIS, PERIOD_UNIT, LIMIT);
        semaphore.acquire();

        for (int invocation = 0; invocation < SHUTDOWN_INVOCATIONS; invocation++) {
            semaphore.shutdown();
        }

        EasyMock.verify(service, timerTask);
    }
}
