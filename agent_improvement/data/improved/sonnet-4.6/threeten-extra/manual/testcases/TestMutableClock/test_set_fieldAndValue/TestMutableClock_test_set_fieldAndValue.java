package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_set_fieldAndValue {

    // Expected date-time components set field-by-field on the clock
    private static final int EXPECTED_YEAR   = 0;
    private static final int EXPECTED_MONTH  = 1;
    private static final int EXPECTED_DAY    = 2;
    private static final int EXPECTED_HOUR   = 3;
    private static final int EXPECTED_MINUTE = 4;
    private static final int EXPECTED_SECOND = 5;

    @Test
    public void test_set_fieldAndValue() {
        // Start from the epoch (1970-01-01T00:00:00Z) and override each field individually
        MutableClock clock = MutableClock.epochUTC();

        clock.set(ChronoField.YEAR,            EXPECTED_YEAR);
        clock.set(ChronoField.MONTH_OF_YEAR,   EXPECTED_MONTH);
        clock.set(ChronoField.DAY_OF_MONTH,    EXPECTED_DAY);
        clock.set(ChronoField.HOUR_OF_DAY,     EXPECTED_HOUR);
        clock.set(ChronoField.MINUTE_OF_HOUR,  EXPECTED_MINUTE);
        clock.set(ChronoField.SECOND_OF_MINUTE, EXPECTED_SECOND);

        assertEquals(
                LocalDateTime.of(EXPECTED_YEAR, EXPECTED_MONTH, EXPECTED_DAY,
                                 EXPECTED_HOUR, EXPECTED_MINUTE, EXPECTED_SECOND)
                             .atZone(ZoneOffset.UTC)
                             .toInstant(),
                clock.instant());
    }
}
