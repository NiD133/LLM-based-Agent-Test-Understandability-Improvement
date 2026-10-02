package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_Chronology_range {

    @Test
    public void test_Chronology_range() {
        assertRange(DAY_OF_WEEK, ValueRange.of(1, 7));
        assertRange(DAY_OF_MONTH, ValueRange.of(1, 28, 31));
        assertRange(MONTH_OF_YEAR, ValueRange.of(1, 12));

        assertRange(DAY_OF_YEAR, ValueRange.of(1, 355, 366));
        assertRange(ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 3, 5));
        assertRange(ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 51, 53));
    }

    private static void assertRange(ChronoField field, ValueRange expectedRange) {
        assertEquals(expectedRange, BritishCutoverChronology.INSTANCE.range(field));
    }
}
