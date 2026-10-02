package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.ThreadUtils;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testStartTimer extends AbstractLangTest {

    private static final long PERIOD_MILLIS = 500;
    private static final Duration PERIOD_DURATION = Duration.ofMillis(PERIOD_MILLIS);
    private static final TimeUnit PERIOD_UNIT = TimeUnit.MILLISECONDS;
    private static final int LIMIT = 10;
    private static final int MAX_TIMER_WAIT_ITERATIONS = 10;

    /**
     * Verifies that starting the timer returns a future and eventually triggers the
     * semaphore's end-of-period callback.
     */
    @Test
    void testStartTimer() throws InterruptedException {
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(PERIOD_MILLIS, PERIOD_UNIT, LIMIT);

        final ScheduledFuture<?> future = semaphore.startTimer();

        assertNotNull(future, "No future returned");
        ThreadUtils.sleepQuietly(PERIOD_DURATION);
        waitForFirstPeriodEnd(semaphore);
        semaphore.shutdown();
    }

    private void waitForFirstPeriodEnd(final TimedSemaphoreTestImpl semaphore) throws InterruptedException {
        int elapsedPeriods = 0;
        do {
            Thread.sleep(PERIOD_MILLIS);
            assertFalse(elapsedPeriods++ > MAX_TIMER_WAIT_ITERATIONS, "endOfPeriod() not called!");
        } while (semaphore.getPeriodEnds() <= 0);
    }

    private static class TimedSemaphoreTestImpl extends TimedSemaphore {

        private int periodEnds;

        TimedSemaphoreTestImpl(final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(timePeriod, timeUnit, limit);
        }

        @Override
        synchronized void endOfPeriod() {
            periodEnds++;
            super.endOfPeriod();
        }

        int getPeriodEnds() {
            return periodEnds;
        }
    }
}
