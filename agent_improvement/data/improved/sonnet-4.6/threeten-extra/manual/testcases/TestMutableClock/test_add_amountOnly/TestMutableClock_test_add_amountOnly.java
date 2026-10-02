package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_add_amountOnly {

    @Test
    public void test_add_amountOnly() {
        MutableClock clock = MutableClock.epochUTC();

        // Apply a sequence of positive, negative, and zero amounts of both Duration and Period types
        clock.add(Duration.ofNanos(3));
        clock.add(Period.ofMonths(2));
        clock.add(Duration.ofSeconds(-5));
        clock.add(Period.ofWeeks(-7));
        clock.add(Duration.ZERO);
        clock.add(Period.ZERO);

        Instant expectedInstant = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC)
                .plusNanos(3)
                .plusMonths(2)
                .minusSeconds(5)
                .minusWeeks(7)
                .toInstant();

        assertEquals(expectedInstant, clock.instant());
    }
}
