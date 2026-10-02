package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focusing on {@link DayOfYear#adjustInto(java.time.temporal.Temporal)}.
 */
public class TestDayOfYear_test_adjustInto_fromStartOfYear_notLeapYear_day366 {

    /** Day-of-year 366 only exists in a leap year. */
    private static final int LEAP_YEAR_LENGTH = 366;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    /**
     * Adjusting a non-leap-year date to day-of-year 366 must fail, because 2007 has
     * only 365 days and therefore has no 366th day.
     */
    @Test
    public void test_adjustInto_fromStartOfYear_notLeapYear_day366() {
        LocalDate startOfNonLeapYear = LocalDate.of(2007, 1, 1);
        DayOfYear day366 = DayOfYear.of(LEAP_YEAR_LENGTH);

        assertThrows(DateTimeException.class, () -> day366.adjustInto(startOfNonLeapYear));
    }
}
