package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_set_adjuster {

    @Test
    public void test_set_adjuster() {
        MutableClock clock = MutableClock.epochUTC();

        // Build up a specific date/time by applying individual adjusters in sequence
        clock.set(LocalDate.of(0, 1, 2));
        clock.set(LocalTime.of(3, 4, 5));
        clock.set(TemporalAdjusters.firstDayOfNextMonth());
        clock.set(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));

        // The expected instant mirrors the same sequence of adjustments applied to a LocalDateTime
        Instant expectedInstant = LocalDateTime.of(0, 1, 2, 3, 4, 5)
                .with(TemporalAdjusters.firstDayOfNextMonth())
                .with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY))
                .atZone(ZoneOffset.UTC)
                .toInstant();
        assertEquals(expectedInstant, clock.instant());

        // Resetting to Instant.EPOCH discards all prior adjustments
        clock.set(Instant.EPOCH);
        assertEquals(Instant.EPOCH, clock.instant());
    }
}
