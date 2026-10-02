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

    /** The period length for the semaphore timer, in milliseconds. */
    private static final long PERIOD_MILLIS = 500;

    /** Duration form of {@link #PERIOD_MILLIS} used for sleeping one full period. */
    private static final Duration PERIOD_DURATION = Duration.ofMillis(PERIOD_MILLIS);

    /** Time unit paired with {@link #PERIOD_MILLIS} when constructing the semaphore. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** Maximum acquire operations allowed per period. */
    private static final int LIMIT = 10;

    /**
     * Maximum number of polling attempts to wait for the timer to fire.
     * Each attempt sleeps one full period, so this caps the total wait at
     * {@code MAX_POLL_ATTEMPTS * PERIOD_MILLIS} ms before the test fails.
     */
    private static final int MAX_POLL_ATTEMPTS = 10;

    /**
     * Verifies that {@link TimedSemaphore#startTimer()} returns a non-null
     * {@link ScheduledFuture} and that the timer actually fires: the internal
     * {@code endOfPeriod()} callback must be invoked at least once within
     * {@link #MAX_POLL_ATTEMPTS} polling cycles.
     */
    @Test
    void testStartTimer() throws InterruptedException {
        final TimedSemaphoreTestImpl semaphore = new TimedSemaphoreTestImpl(PERIOD_MILLIS, UNIT, LIMIT);

        // Start the timer and confirm a valid future is returned.
        final ScheduledFuture<?> future = semaphore.startTimer();
        assertNotNull(future, "No future returned");

        // Allow at least one period to elapse so the scheduler can settle.
        ThreadUtils.sleepQuietly(PERIOD_DURATION);

        // Poll every period until endOfPeriod() has been called at least once,
        // failing if it never happens within MAX_POLL_ATTEMPTS attempts.
        int pollCount = 0;
        do {
            Thread.sleep(PERIOD_MILLIS);
            assertFalse(pollCount++ > MAX_POLL_ATTEMPTS, "endOfPeriod() not called!");
        } while (semaphore.getPeriodEnds() <= 0);

        semaphore.shutdown();
    }
}
