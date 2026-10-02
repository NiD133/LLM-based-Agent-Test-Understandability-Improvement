package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_adjustInto_fromEndOfYear_notLeapYear_day366 {

    // Day 366 only exists in leap years; 2007 is not a leap year
    private static final int LEAP_YEAR_LENGTH = 366;

    @Test
    public void test_adjustInto_fromEndOfYear_notLeapYear_day366() {
        // base date is the last day of a standard (non-leap) year
        LocalDate lastDayOfNonLeapYear = LocalDate.of(2007, 12, 31);
        // day 366 is invalid for non-leap years, so adjustInto must throw
        DayOfYear leapOnlyDay = DayOfYear.of(LEAP_YEAR_LENGTH);
        assertThrows(DateTimeException.class, () -> leapOnlyDay.adjustInto(lastDayOfNonLeapYear));
    }
}
