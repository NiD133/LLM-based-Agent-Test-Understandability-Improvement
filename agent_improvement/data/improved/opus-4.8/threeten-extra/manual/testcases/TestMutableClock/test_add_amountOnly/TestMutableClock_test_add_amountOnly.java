package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MutableClock#add(java.time.temporal.TemporalAmount)}.
 * <p>
 * A {@code MutableClock} applies each {@code add} call by interpreting its
 * instant as a {@link ZonedDateTime}, adding the amount, then converting back
 * to an instant. This test applies a sequence of {@link Duration} and
 * {@link Period} amounts (including positive, negative and zero values) and
 * confirms the clock ends up at the instant produced by applying the same
 * sequence to the starting date-time.
 */
public class TestMutableClock_test_add_amountOnly {

    @Test
    public void test_add_amountOnly() {
        // Clock starts at the epoch (1970-01-01T00:00:00Z) in the UTC zone.
        MutableClock clock = MutableClock.epochUTC();

        // Apply a mix of duration- and period-based amounts in order.
        clock.add(Duration.ofNanos(3));     // +3 nanoseconds
        clock.add(Period.ofMonths(2));      // +2 months
        clock.add(Duration.ofSeconds(-5));  // -5 seconds
        clock.add(Period.ofWeeks(-7));      // -7 weeks
        clock.add(Duration.ZERO);           // no-op
        clock.add(Period.ZERO);             // no-op

        // The expected instant is the same sequence applied to the epoch in UTC.
        Instant expected = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC)
                .plusNanos(3)
                .plusMonths(2)
                .minusSeconds(5)
                .minusWeeks(7)
                .toInstant();

        assertEquals(expected, clock.instant());
    }
}
