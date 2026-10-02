package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfMonth#atYearMonth(YearMonth)} for the 28th day-of-month.
 * <p>
 * The 28th is a valid day in every month of every year (it is the shortest
 * possible month length), so combining day 28 with any year-month must always
 * yield the 28th of that exact month, with no clamping to the month's end.
 */
public class TestDayOfMonth_test_atYearMonth_28 {

    //-----------------------------------------------------------------------
    // now() / now(ZoneId): the current day-of-month must match LocalDate's.
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    // atYearMonth(): day 28 maps to the 28th of every month in the year.
    //-----------------------------------------------------------------------
    @Test
    public void test_atYearMonth_28() {
        DayOfMonth day28 = DayOfMonth.of(28);
        int year = 2012;

        for (int month = 1; month <= 12; month++) {
            LocalDate expected = LocalDate.of(year, month, 28);
            LocalDate actual = day28.atYearMonth(YearMonth.of(year, month));
            assertEquals(expected, actual);
        }
    }
}
