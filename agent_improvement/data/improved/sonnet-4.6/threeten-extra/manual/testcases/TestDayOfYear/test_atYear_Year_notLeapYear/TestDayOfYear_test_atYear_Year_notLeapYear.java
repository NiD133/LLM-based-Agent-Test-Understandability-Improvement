package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_atYear_Year_notLeapYear {

    private static final Year YEAR_STANDARD = Year.of(2007);

    private static final int STANDARD_YEAR_LENGTH = 365;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    // Verifies that every day-of-year (1–365) resolves to the correct LocalDate in a standard (non-leap) year.
    @Test
    public void test_atYear_Year_notLeapYear() {
        LocalDate expected = LocalDate.of(2007, 1, 1);
        for (int dayNumber = 1; dayNumber <= STANDARD_YEAR_LENGTH; dayNumber++) {
            DayOfYear test = DayOfYear.of(dayNumber);
            assertEquals(expected, test.atYear(YEAR_STANDARD));
            expected = expected.plusDays(1);
        }
    }
}
