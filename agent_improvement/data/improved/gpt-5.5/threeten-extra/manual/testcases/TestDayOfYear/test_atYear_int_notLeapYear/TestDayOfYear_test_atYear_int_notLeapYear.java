package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_atYear_int_notLeapYear {

    private static final int NON_LEAP_YEAR = 2007;
    private static final int NON_LEAP_YEAR_LENGTH = 365;

    @Test
    public void test_atYear_int_notLeapYear() {
        LocalDate expectedDate = LocalDate.of(NON_LEAP_YEAR, 1, 1);

        for (int dayOfYear = 1; dayOfYear <= NON_LEAP_YEAR_LENGTH; dayOfYear++) {
            DayOfYear test = DayOfYear.of(dayOfYear);

            assertEquals(expectedDate, test.atYear(NON_LEAP_YEAR));
            expectedDate = expectedDate.plusDays(1);
        }
    }
}
