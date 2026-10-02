package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Year;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_atYear_fromStartOfYear_notLeapYear_day366 {

    private static final Year STANDARD_YEAR = Year.of(2007);
    private static final int LEAP_YEAR_LENGTH = 366;

    @Test
    public void test_atYear_fromStartOfYear_notLeapYear_day366() {
        DayOfYear leapDay = DayOfYear.of(LEAP_YEAR_LENGTH);

        assertThrows(DateTimeException.class, () -> leapDay.atYear(STANDARD_YEAR));
    }
}
