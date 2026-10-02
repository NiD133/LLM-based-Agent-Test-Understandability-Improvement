package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_from_TemporalAccessor_notLeapYear {

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
    public void test_from_TemporalAccessor_notLeapYear() {
        // 2007 is not a leap year, so February has 28 days
        LocalDate date = LocalDate.of(2007, 1, 1);
        for (Month month : Month.values()) {
            int daysInMonth = month.length(false);
            for (int day = 1; day <= daysInMonth; day++) {
                assertEquals(day, DayOfMonth.from(date).getValue());
                date = date.plusDays(1);
            }
        }
    }
}
