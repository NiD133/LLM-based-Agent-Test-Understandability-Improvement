package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth#isValidYearMonth(YearMonth)} using the 28th day-of-month.
 *
 * <p>The 28th day exists in every calendar month, so it must be reported as valid for
 * each of the twelve months of any year (here the leap year 2012).
 */
public class TestDayOfMonth_test_isValidYearMonth_28 {

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_isValidYearMonth_28() {
        DayOfMonth day28 = DayOfMonth.of(28);

        // The 28th is a valid day in all 12 months, so isValidYearMonth must return true for each.
        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(2012, month);
            assertEquals(true, day28.isValidYearMonth(yearMonth),
                    "the 28th should be valid for " + yearMonth);
        }
    }
}
