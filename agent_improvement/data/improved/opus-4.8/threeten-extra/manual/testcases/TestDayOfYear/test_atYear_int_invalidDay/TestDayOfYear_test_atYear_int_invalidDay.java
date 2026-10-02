package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focusing on {@link DayOfYear#atYear(int)} with an
 * out-of-range year.
 */
public class TestDayOfYear_test_atYear_int_invalidDay {

    /** An arbitrary, valid day-of-year used as the receiver under test. */
    private static final DayOfYear DAY_OF_YEAR_12 = DayOfYear.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_returnsCurrentDayOfYearInDefaultZone() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void now_withZone_returnsCurrentDayOfYearInThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // atYear(int)
    //-----------------------------------------------------------------------
    @Test
    public void atYear_int_throwsWhenYearBelowMinimum() {
        // A year smaller than Year.MIN_VALUE is not a valid ISO year, so atYear must reject it.
        int yearBelowMinimum = Year.MIN_VALUE - 1;

        assertThrows(DateTimeException.class, () -> DAY_OF_YEAR_12.atYear(yearBelowMinimum));
    }
}
