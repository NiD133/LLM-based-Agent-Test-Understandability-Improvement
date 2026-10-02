package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfMonth#range(java.time.temporal.TemporalField)} with an
 * unsupported field, plus the related {@code now()} factory methods.
 */
public class TestDayOfMonth_test_range_invalidField {

    /** A fixed sample day-of-month used by the range test. */
    private static final DayOfMonth SAMPLE_DAY = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_returnsCurrentDayOfMonth() {
        int expectedDay = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void now_withZone_returnsCurrentDayOfMonthInThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDay = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // range(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void range_unsupportedField_throwsUnsupportedTemporalTypeException() {
        // MONTH_OF_YEAR is not a field that a DayOfMonth can supply a range for.
        assertThrows(UnsupportedTemporalTypeException.class, () -> SAMPLE_DAY.range(MONTH_OF_YEAR));
    }
}
