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
        // DAY_OF_WEEK is always 1–7 (standard 7-day week, no variation)
        assertEquals(ValueRange.of(1, 7), PaxChronology.INSTANCE.range(DAY_OF_WEEK));

        // DAY_OF_MONTH is 1–7 in the one-week Pax leap month, 1–28 in all other months
        assertEquals(ValueRange.of(1, 7, 28), PaxChronology.INSTANCE.range(DAY_OF_MONTH));

        // DAY_OF_YEAR is 1–364 in standard years, 1–371 in leap years (364 + 7 extra days)
        assertEquals(ValueRange.of(1, 364, 371), PaxChronology.INSTANCE.range(DAY_OF_YEAR));

        // MONTH_OF_YEAR is 1–13 in standard years, 1–14 in leap years (extra Pax month inserted)
        assertEquals(ValueRange.of(1, 13, 14), PaxChronology.INSTANCE.range(MONTH_OF_YEAR));
    }
}
