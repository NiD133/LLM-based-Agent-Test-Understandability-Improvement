package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_from_TemporalAccessor_leapYear {

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

    @Test
    public void test_from_TemporalAccessor_leapYear() {
        // 2008 is a leap year, so February has 29 days instead of 28
        int leapYear = 2008;
        LocalDate date = LocalDate.of(leapYear, Month.JANUARY, 1);

        Month[] monthsToTest = {Month.JANUARY, Month.FEBRUARY, Month.MARCH};
        for (Month month : monthsToTest) {
            int daysInMonth = YearMonth.of(leapYear, month).lengthOfMonth();
            for (int day = 1; day <= daysInMonth; day++) {
                assertEquals(day, DayOfMonth.from(date).getValue(),
                        "Expected day-of-month " + day + " for " + month + " " + leapYear);
                date = date.plusDays(1);
            }
        }
    }
}
