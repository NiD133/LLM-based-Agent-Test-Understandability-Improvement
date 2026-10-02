package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MutableClock#set(TemporalAdjuster)}.
 * <p>
 * Each {@code set(adjuster)} call applies the adjuster to the clock's current
 * date-time (interpreted in the clock's time-zone) and stores the resulting
 * instant, mirroring {@code ZonedDateTime.with(adjuster)}.
 */
public class TestMutableClock_test_set_adjuster {

    @Test
    public void test_set_adjuster() {
        // A UTC clock starting at the epoch (1970-01-01T00:00:00Z).
        MutableClock clock = MutableClock.epochUTC();

        // Apply a sequence of adjusters that build up a specific date-time.
        clock.set(LocalDate.of(0, 1, 2));                       // set the date
        clock.set(LocalTime.of(3, 4, 5));                       // set the time
        clock.set(TemporalAdjusters.firstDayOfNextMonth());     // jump to start of next month
        clock.set(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)); // advance to the next Wednesday

        // The clock's instant must equal the same adjusters applied to a
        // ZonedDateTime built from the starting date-time in UTC.
        Instant expectedInstant = LocalDateTime.of(0, 1, 2, 3, 4, 5)
                .with(TemporalAdjusters.firstDayOfNextMonth())
                .with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY))
                .atZone(ZoneOffset.UTC)
                .toInstant();
        assertEquals(expectedInstant, clock.instant());

        // Setting the clock directly to an instant overrides the date-time above.
        clock.set(Instant.EPOCH);
        assertEquals(Instant.EPOCH, clock.instant());
    }
}
