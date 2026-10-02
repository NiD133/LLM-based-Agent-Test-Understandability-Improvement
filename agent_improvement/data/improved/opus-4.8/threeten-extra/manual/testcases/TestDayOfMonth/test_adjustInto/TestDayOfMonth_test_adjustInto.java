package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_adjustInto {

    /** The longest possible month, used to bound the day-of-month loop. */
    private static final int DAYS_IN_LONGEST_MONTH = 31;

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
    public void test_adjustInto() {
        // Adjusting a fixed January date to day-of-month "i" should produce
        // the i-th day of that same January (January 2007 has 31 days, so every
        // value from 1 to 31 stays within the month).
        LocalDate january2007 = LocalDate.of(2007, 1, 1);

        for (int dayOfMonth = 1; dayOfMonth <= DAYS_IN_LONGEST_MONTH; dayOfMonth++) {
            Temporal adjusted = DayOfMonth.of(dayOfMonth).adjustInto(january2007);

            LocalDate expectedDate = LocalDate.of(2007, 1, dayOfMonth);
            assertEquals(expectedDate, adjusted);
        }
    }
}
