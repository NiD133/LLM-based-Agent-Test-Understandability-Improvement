package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfYear#getLong(java.time.temporal.TemporalField)} and the related
 * {@code now} factory methods.
 */
public class TestDayOfYear_test_getLong {

    /** A fixed day-of-year used as the subject for the getLong assertion. */
    private static final DayOfYear DAY_12 = DayOfYear.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesSystemClockDayOfYear() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_withZone_matchesDayOfYearInThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // getLong(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void getLong_dayOfYearField_returnsTheDayValue() {
        assertEquals(12L, DAY_12.getLong(DAY_OF_YEAR));
    }
}
