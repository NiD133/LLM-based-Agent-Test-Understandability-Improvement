package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for reading values from a {@link DayOfYear}.
 */
public class TestDayOfYear_test_get {

    /** A fixed day-of-year (the 12th day) used as the subject under test. */
    private static final DayOfYear DAY_12 = DayOfYear.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesCurrentDayOfYear() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_withZone_matchesCurrentDayOfYearInThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // get(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void get_dayOfYearField_returnsValue() {
        assertEquals(12, DAY_12.get(DAY_OF_YEAR));
    }
}
