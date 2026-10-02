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

        LocalDate initialDate = LocalDate.of(0, 1, 2);
        LocalTime initialTime = LocalTime.of(3, 4, 5);
        clock.set(initialDate);
        clock.set(initialTime);

        clock.set(TemporalAdjusters.firstDayOfNextMonth());
        clock.set(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));

        LocalDateTime initialDateTime = LocalDateTime.of(0, 1, 2, 3, 4, 5);
        Instant expectedAdjustedInstant = initialDateTime
                .with(TemporalAdjusters.firstDayOfNextMonth())
                .with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY))
                .atZone(ZoneOffset.UTC)
                .toInstant();
        assertEquals(expectedAdjustedInstant, clock.instant());

        clock.set(Instant.EPOCH);
        assertEquals(Instant.EPOCH, clock.instant());
    }
}
