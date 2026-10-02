package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MutableClock#add(long, java.time.temporal.TemporalUnit)}.
 * <p>
 * A series of additions (some positive, some negative, some zero) is applied to
 * a clock that starts at the epoch. The resulting instant must match the same
 * sequence of operations applied directly to the equivalent {@link ZonedDateTime}.
 */
public class TestMutableClock_test_add_amountAndUnit {

    @Test
    public void test_add_amountAndUnit() {
        // A clock starting at 1970-01-01T00:00:00Z in the UTC zone.
        MutableClock clock = MutableClock.epochUTC();

        // Apply a mix of positive, negative, and zero-valued additions.
        clock.add(3, ChronoUnit.NANOS);
        clock.add(2, ChronoUnit.MONTHS);
        clock.add(-5, ChronoUnit.SECONDS);
        clock.add(-7, ChronoUnit.WEEKS);
        clock.add(0, ChronoUnit.MILLIS);  // no-op
        clock.add(0, ChronoUnit.YEARS);   // no-op

        // The expected instant is the same operations applied to a ZonedDateTime.
        Instant expectedInstant = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC)
                .plusNanos(3)
                .plusMonths(2)
                .minusSeconds(5)
                .minusWeeks(7)
                .toInstant();

        assertEquals(expectedInstant, clock.instant());
    }
}
