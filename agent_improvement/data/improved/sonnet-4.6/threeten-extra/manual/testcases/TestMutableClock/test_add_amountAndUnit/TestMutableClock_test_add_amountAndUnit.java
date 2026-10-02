package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_add_amountAndUnit {

    @Test
    public void test_add_amountAndUnit() {
        // Setup: clock starting at the Unix epoch in UTC
        MutableClock clock = MutableClock.epochUTC();

        // Act: apply a mix of positive, negative, and zero additions across various units
        clock.add(3, ChronoUnit.NANOS);
        clock.add(2, ChronoUnit.MONTHS);
        clock.add(-5, ChronoUnit.SECONDS);
        clock.add(-7, ChronoUnit.WEEKS);
        clock.add(0, ChronoUnit.MILLIS);   // no-op: zero amount
        clock.add(0, ChronoUnit.YEARS);    // no-op: zero amount

        // Assert: final instant matches the cumulative effect of all additions
        Instant expected = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC)
                .plusNanos(3)
                .plusMonths(2)
                .minusSeconds(5)
                .minusWeeks(7)
                .toInstant();

        assertEquals(expected, clock.instant());
    }
}
