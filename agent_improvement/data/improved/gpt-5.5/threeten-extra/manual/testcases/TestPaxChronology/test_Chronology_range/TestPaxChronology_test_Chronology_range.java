package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_Chronology_range {

    @Test
    public void test_Chronology_range() {
        assertEquals(ValueRange.of(1, 7), PaxChronology.INSTANCE.range(DAY_OF_WEEK));
        assertEquals(ValueRange.of(1, 7, 28), PaxChronology.INSTANCE.range(DAY_OF_MONTH));
        assertEquals(ValueRange.of(1, 364, 371), PaxChronology.INSTANCE.range(DAY_OF_YEAR));
        assertEquals(ValueRange.of(1, 13, 14), PaxChronology.INSTANCE.range(MONTH_OF_YEAR));
    }
}
