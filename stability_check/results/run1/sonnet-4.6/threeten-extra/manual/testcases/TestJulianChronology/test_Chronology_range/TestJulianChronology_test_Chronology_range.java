package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Tests that JulianChronology.range() returns the correct field value ranges
 * for the Julian calendar system.
 */
public class TestJulianChronology_test_Chronology_range {

    @Test
    public void test_Chronology_range() {
        // Day of week: always 1–7 (Monday to Sunday)
        assertEquals(ValueRange.of(1, 7), JulianChronology.INSTANCE.range(DAY_OF_WEEK));

        // Day of month: varies by month (28 to 31), Julian Feb can reach 29 in leap years
        assertEquals(ValueRange.of(1, 28, 31), JulianChronology.INSTANCE.range(DAY_OF_MONTH));

        // Day of year: 365 in common years, 366 in Julian leap years (every 4 years)
        assertEquals(ValueRange.of(1, 365, 366), JulianChronology.INSTANCE.range(DAY_OF_YEAR));

        // Month of year: always 1–12
        assertEquals(ValueRange.of(1, 12), JulianChronology.INSTANCE.range(MONTH_OF_YEAR));
    }
}
