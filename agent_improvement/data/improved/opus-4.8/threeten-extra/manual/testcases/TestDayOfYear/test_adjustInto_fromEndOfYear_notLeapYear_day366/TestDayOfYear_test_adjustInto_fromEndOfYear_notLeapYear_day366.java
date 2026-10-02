package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focusing on {@code adjustInto} together with the
 * {@code now} factory methods.
 */
public class TestDayOfYear_test_adjustInto_fromEndOfYear_notLeapYear_day366 {

    /** The 366th day, which only exists in leap years. */
    private static final int LEAP_YEAR_LENGTH = 366;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        // DayOfYear.now() must agree with LocalDate.now() in the default zone.
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        // DayOfYear.now(zone) must agree with LocalDate.now(zone) for the same zone.
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_adjustInto_fromEndOfYear_notLeapYear_day366() {
        // 2007 is not a leap year, so adjusting any date in it to day 366 is invalid.
        LocalDate dateInNonLeapYear = LocalDate.of(2007, 12, 31);
        DayOfYear day366 = DayOfYear.of(LEAP_YEAR_LENGTH);

        assertThrows(DateTimeException.class, () -> day366.adjustInto(dateInNonLeapYear));
    }
}
