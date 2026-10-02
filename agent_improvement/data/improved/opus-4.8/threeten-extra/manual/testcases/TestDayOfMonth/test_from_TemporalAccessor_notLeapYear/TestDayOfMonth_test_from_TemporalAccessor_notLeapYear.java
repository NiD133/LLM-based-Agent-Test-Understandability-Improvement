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
        // Walk every day of a non-leap year (2007) and confirm that DayOfMonth.from(date)
        // reports the day-of-month of that date, for each month's correct (non-leap) length.
        boolean leapYear = false;
        LocalDate date = LocalDate.of(2007, 1, 1);

        for (Month month : Month.values()) {
            int lengthOfMonth = month.length(leapYear);
            for (int expectedDayOfMonth = 1; expectedDayOfMonth <= lengthOfMonth; expectedDayOfMonth++) {
                assertEquals(expectedDayOfMonth, DayOfMonth.from(date).getValue());
                date = date.plusDays(1);
            }
        }
    }
}
