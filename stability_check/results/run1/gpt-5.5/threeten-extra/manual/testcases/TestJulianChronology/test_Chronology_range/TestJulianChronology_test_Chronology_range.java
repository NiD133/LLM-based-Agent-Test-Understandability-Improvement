package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_Chronology_range {

    @Test
    public void test_Chronology_range() {
        assertChronologyRange(DAY_OF_WEEK, ValueRange.of(1, 7));
        assertChronologyRange(DAY_OF_MONTH, ValueRange.of(1, 28, 31));
        assertChronologyRange(DAY_OF_YEAR, ValueRange.of(1, 365, 366));
        assertChronologyRange(MONTH_OF_YEAR, ValueRange.of(1, 12));
    }

    private void assertChronologyRange(ChronoField field, ValueRange expectedRange) {
        assertEquals(expectedRange, JulianChronology.INSTANCE.range(field));
    }
}
