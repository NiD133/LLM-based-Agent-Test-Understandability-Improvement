package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_adjustInto_fromStartOfYear_notLeapYear {

    // A standard (non-leap) year has 365 days
    private static final int STANDARD_YEAR_LENGTH = 365;

    @Test
    public void test_adjustInto_fromStartOfYear_notLeapYear() {
        LocalDate startOfNonLeapYear = LocalDate.of(2007, 1, 1);
        LocalDate expectedDate = startOfNonLeapYear;
        for (int dayNumber = 1; dayNumber <= STANDARD_YEAR_LENGTH; dayNumber++) {
            DayOfYear dayOfYear = DayOfYear.of(dayNumber);
            assertEquals(expectedDate, dayOfYear.adjustInto(startOfNonLeapYear));
            expectedDate = expectedDate.plusDays(1);
        }
    }
}
