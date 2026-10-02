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
 * Tests for {@link DayOfMonth} focusing on querying field values.
 */
public class TestDayOfMonth_test_get_invalidField {

    /** A fixed day-of-month (the 12th) used as the subject under test. */
    private static final DayOfMonth DAY_12 = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesSystemClockDayOfMonth() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void now_withZone_matchesClockDayOfMonthInThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // get(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void get_withUnsupportedField_throwsUnsupportedTemporalType() {
        // DayOfMonth only supports DAY_OF_MONTH, so querying MONTH_OF_YEAR must fail.
        assertThrows(UnsupportedTemporalTypeException.class, () -> DAY_12.get(MONTH_OF_YEAR));
    }
}
