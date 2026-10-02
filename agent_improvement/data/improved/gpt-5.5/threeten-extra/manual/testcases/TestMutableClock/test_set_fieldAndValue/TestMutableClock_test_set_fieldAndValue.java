package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_set_fieldAndValue {

    @Test
    public void test_set_fieldAndValue() {
        MutableClock clock = MutableClock.epochUTC();

        clock.set(ChronoField.YEAR, 0);
        clock.set(ChronoField.MONTH_OF_YEAR, 1);
        clock.set(ChronoField.DAY_OF_MONTH, 2);
        clock.set(ChronoField.HOUR_OF_DAY, 3);
        clock.set(ChronoField.MINUTE_OF_HOUR, 4);
        clock.set(ChronoField.SECOND_OF_MINUTE, 5);

        Instant expectedInstant = LocalDateTime.of(0, 1, 2, 3, 4, 5)
                .atZone(ZoneOffset.UTC)
                .toInstant();
        assertEquals(expectedInstant, clock.instant());
    }
}
