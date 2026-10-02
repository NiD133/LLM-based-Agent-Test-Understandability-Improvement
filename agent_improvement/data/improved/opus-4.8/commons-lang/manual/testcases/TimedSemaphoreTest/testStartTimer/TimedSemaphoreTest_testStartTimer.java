package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.ThreadUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link TimedSemaphore#startTimer()}.
 */
public class TimedSemaphoreTest_testStartTimer extends AbstractLangTest {

    /** Length of one semaphore period, in milliseconds. */
    private static final long PERIOD_MILLIS = 500;

    /** The same period expressed as a {@link Duration}. */
    private static final Duration PERIOD_DURATION = Duration.ofMillis(PERIOD_MILLIS);

    /** Time unit matching {@link #PERIOD_MILLIS}. */
    private static final TimeUnit PERIOD_UNIT = TimeUnit.MILLISECONDS;

    /** Number of permits allowed per period. */
    private static final int LIMIT = 10;

    /** How many extra period-length waits to allow before giving up on the timer. */
    private static final int MAX_EXTRA_WAITS = 10;

    /**
     * A {@link TimedSemaphore} subclass that records how often the scheduled
     * {@code endOfPeriod()} callback has fired, so tests can observe the timer running.
     */
    private static final class TimedSemaphoreTestImpl extends TimedSemaphore {

        /** Number of times {@link #endOfPeriod()} has been invoked by the timer. */
        private int periodEnds;

        TimedSemaphoreTestImpl(final long timePeriod, final TimeUnit timeUnit, final int limit) {
            super(timePeriod, timeUnit, limit);
        }

        /**
         * Gets the number of completed periods observed so far.
         *
         * @return the count of {@code endOfPeriod()} invocations.
         */
        synchronized int getPeriodEnds() {
            return periodEnds;
        }

        @Override
        synchronized void endOfPeriod() {
            super.endOfPeriod();
            periodEnds++;
        }
    }

    /**
     * Verifies that {@link TimedSemaphore#startTimer()} schedules a recurring task: the
     * returned future is non-null, and within a bounded number of periods the scheduled
     * {@code endOfPeriod()} callback fires (observed via {@code getPeriodEnds() > 0}).
     *
     * @throws InterruptedException so we don't have to catch it.
     */
    @Test
    void testStartTimer() throws InterruptedException {
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(PERIOD_MILLIS, PERIOD_UNIT, LIMIT);

        final ScheduledFuture<?> timerTask = semaphore.startTimer();
        assertNotNull(timerTask, "No future returned");

        // Give the timer roughly one period to fire for the first time.
        ThreadUtils.sleepQuietly(PERIOD_DURATION);

        // Then poll once per period until the end-of-period callback has run, bounding the
        // total wait so a never-firing timer fails the test instead of hanging forever.
        int extraWaits = 0;
        do {
            Thread.sleep(PERIOD_MILLIS);
            assertFalse(extraWaits++ > MAX_EXTRA_WAITS, "endOfPeriod() not called!");
        } while (semaphore.getPeriodEnds() <= 0);

        semaphore.shutdown();
    }
}
