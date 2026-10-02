/*
 * Refactored for understandability from the EvoSuite-generated test.
 * All method calls, arguments, and expected outcomes are preserved exactly.
 */

package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.time.DurationUtils;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest extends DurationUtils_ESTest_scaffolding {

    /**
     * getMillis with a non-existent property key returns a Duration equal to the default value.
     * toMillisLong then converts that Duration back to the same long without overflow.
     */
    @Test(timeout = 4000)
    public void toMillisLong_returnsDefaultMillis_whenPropertyKeyDoesNotExist() throws Throwable {
        Duration duration = DurationUtils.getMillis("g\"*8?I", Long.MIN_VALUE);
        long millis = DurationUtils.toMillisLong(duration);
        assertEquals(Long.MIN_VALUE, millis);
    }

    /**
     * ChronoUnit.FOREVER has a duration that overflows long milliseconds.
     * toMillisLong must clamp the result to Long.MAX_VALUE instead of throwing.
     */
    @Test(timeout = 4000)
    public void toMillisLong_returnsLongMaxValue_whenDurationOverflowsMilliseconds() throws Throwable {
        Duration foreverDuration = ChronoUnit.FOREVER.getDuration();
        long millis = DurationUtils.toMillisLong(foreverDuration);
        assertEquals(Long.MAX_VALUE, millis);
    }

    @Test(timeout = 4000)
    public void toChronoUnit_returnsDays_forTimeUnitDays() throws Throwable {
        ChronoUnit chronoUnit = DurationUtils.toChronoUnit(TimeUnit.DAYS);
        assertEquals(ChronoUnit.DAYS, chronoUnit);
    }

    @Test(timeout = 4000)
    public void toChronoUnit_returnsHours_forTimeUnitHours() throws Throwable {
        ChronoUnit chronoUnit = DurationUtils.toChronoUnit(TimeUnit.HOURS);
        assertEquals(ChronoUnit.HOURS, chronoUnit);
    }

    @Test(timeout = 4000)
    public void toChronoUnit_returnsSeconds_forTimeUnitSeconds() throws Throwable {
        ChronoUnit chronoUnit = DurationUtils.toChronoUnit(TimeUnit.SECONDS);
        assertEquals(ChronoUnit.SECONDS, chronoUnit);
    }

    @Test(timeout = 4000)
    public void toChronoUnit_returnsMicros_forTimeUnitMicroseconds() throws Throwable {
        ChronoUnit chronoUnit = DurationUtils.toChronoUnit(TimeUnit.MICROSECONDS);
        assertEquals(ChronoUnit.MICROS, chronoUnit);
    }

    @Test(timeout = 4000)
    public void toChronoUnit_returnsNanos_forTimeUnitNanoseconds() throws Throwable {
        ChronoUnit chronoUnit = DurationUtils.toChronoUnit(TimeUnit.NANOSECONDS);
        assertEquals(ChronoUnit.NANOS, chronoUnit);
    }

    /**
     * A duration with both negative seconds and negative nanosecond adjustment is not positive.
     */
    @Test(timeout = 4000)
    public void isPositive_returnsFalse_forNegativeDuration() throws Throwable {
        Duration negativeDuration = Duration.ofSeconds(-105L, -105L);
        boolean result = DurationUtils.isPositive(negativeDuration);
        assertFalse(result);
    }

    /**
     * getMillis accepts a null key and falls back to the given default value.
     */
    @Test(timeout = 4000)
    public void getMillis_returnsNonNull_whenPropertyKeyIsNull() throws Throwable {
        Duration duration = DurationUtils.getMillis((String) null, -1048L);
        assertNotNull(duration);
    }

    /**
     * accept() is a no-op when the duration argument is null (guard inside accept).
     */
    @Test(timeout = 4000)
    public void accept_doesNotThrow_whenDurationIsNull() throws Throwable {
        FailableBiConsumer<Long, Integer, Throwable> nopConsumer = FailableBiConsumer.nop();
        DurationUtils.accept(nopConsumer, (Duration) null);
        // ABSTAINED
    }

    /**
     * accept() is a no-op when the consumer argument is null (guard inside accept).
     */
    @Test(timeout = 4000)
    public void accept_doesNotThrow_whenConsumerIsNull() throws Throwable {
        DurationUtils.accept((FailableBiConsumer<Long, Integer, Throwable>) null, Duration.ZERO);
        // ABSTAINED
    }

    /**
     * get() delegates to the system property (falling back to the default) and wraps
     * the value as a Duration in the given ChronoUnit.
     */
    @Test(timeout = 4000)
    public void get_returnsNonNull_forNonExistentPropertyKeyWithMillisUnit() throws Throwable {
        ChronoUnit millisUnit = DurationUtils.toChronoUnit(TimeUnit.MILLISECONDS);
        Duration duration = DurationUtils.get("_\"/5Q'", millisUnit, -210L);
        assertNotNull(duration);
    }

    /**
     * of(FailableRunnable) measures execution time; a mock runnable that does nothing
     * executes in effectively zero time, so the resulting duration is not positive.
     */
    @Test(timeout = 4000)
    public void isPositive_returnsFalse_forDurationOfMockedRunnable() throws Throwable {
        FailableRunnable<Throwable> noOpRunnable =
                (FailableRunnable<Throwable>) mock(FailableRunnable.class, new ViolatedAssumptionAnswer());
        Duration elapsed = DurationUtils.of(noOpRunnable);
        boolean result = DurationUtils.isPositive(elapsed);
        assertFalse(result);
    }

    /**
     * getMillis with a positive default returns a positive Duration.
     */
    @Test(timeout = 4000)
    public void isPositive_returnsTrue_forGetMillisWithPositiveDefault() throws Throwable {
        Duration duration = DurationUtils.getMillis("ctJ#Rib]z0+G8", 3831L);
        boolean result = DurationUtils.isPositive(duration);
        assertTrue(result);
    }

    /**
     * -866 minutes in milliseconds is -51,960,000 ms, which fits in an int.
     */
    @Test(timeout = 4000)
    public void toMillisInt_returnsExpectedIntValue_forNegativeMinutesDuration() throws Throwable {
        Duration negativeMinutesDuration = DurationUtils.toDuration(-866L, TimeUnit.MINUTES);
        int millis = DurationUtils.toMillisInt(negativeMinutesDuration);
        assertEquals(-51960000, millis);
    }

    /**
     * of(FailableConsumer) times a no-op consumer starting now; the nanoseconds-of-milli
     * part of such a near-zero duration is 0.
     */
    @Test(timeout = 4000)
    public void getNanosOfMiili_returnsZero_forNearZeroElapsedDuration() throws Throwable {
        FailableConsumer<Instant, Throwable> nopConsumer = FailableConsumer.nop();
        Duration elapsed = DurationUtils.of(nopConsumer);
        int nanosOfMilli = DurationUtils.getNanosOfMiili(elapsed);
        assertEquals(0, nanosOfMilli);
    }

    @Test(timeout = 4000)
    public void constructor_canBeInstantiated() throws Throwable {
        new DurationUtils();
        // ABSTAINED
    }

    /**
     * accept() correctly passes the milliseconds and nanoseconds of a seconds-based Duration
     * to a nop consumer without throwing.
     */
    @Test(timeout = 4000)
    public void accept_doesNotThrow_withNopConsumerAndSecondsDuration() throws Throwable {
        Duration secondsDuration = DurationUtils.getSeconds("_\"/5Q'", -913L);
        FailableBiConsumer<Long, Integer, Throwable> nopConsumer = FailableBiConsumer.nop();
        DurationUtils.accept(nopConsumer, secondsDuration);
        // ABSTAINED
    }
}
