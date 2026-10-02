package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_atYear_int_fromStartOfYear_notLeapYear_day366 {

    private static final int NOT_LEAP_YEAR = 2007;
    private static final int LAST_DAY_OF_LEAP_YEAR = 366;

    @Test
    public void test_atYear_int_fromStartOfYear_notLeapYear_day366() {
        DayOfYear leapYearOnlyDay = DayOfYear.of(LAST_DAY_OF_LEAP_YEAR);

        assertThrows(DateTimeException.class, () -> leapYearOnlyDay.atYear(NOT_LEAP_YEAR));
    }
}
