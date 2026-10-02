package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Year;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_atYear_Year_notLeapYear {

    // 2007 is a standard (non-leap) year with 365 days
    private static final Year YEAR_STANDARD = Year.of(2007);
    private static final int STANDARD_YEAR_LENGTH = 365;

    /**
     * Verifies that atYear(Year) correctly maps every day-of-year (1–365) to the
     * corresponding calendar date when the target year is not a leap year.
     * The expected date advances one day at a time starting from January 1st.
     */
    @Test
    public void test_atYear_Year_notLeapYear() {
        LocalDate expected = LocalDate.of(2007, 1, 1);
        for (int i = 1; i <= STANDARD_YEAR_LENGTH; i++) {
            DayOfYear test = DayOfYear.of(i);
            assertEquals(expected, test.atYear(YEAR_STANDARD));
            expected = expected.plusDays(1);
        }
    }
}
