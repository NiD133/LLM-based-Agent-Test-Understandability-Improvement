package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_adjustInto_february29_notLeapYear {

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfMonth = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfMonth = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // adjustInto(Temporal)
    //-----------------------------------------------------------------------
    @Test
    public void test_adjustInto_february29_notLeapYear() {
        // 2007 is not a leap year, so February has only 28 days.
        // Adjusting a February date to the 29th is therefore invalid.
        LocalDate februaryDate = LocalDate.of(2007, 2, 1);
        DayOfMonth day29 = DayOfMonth.of(29);

        assertThrows(DateTimeException.class, () -> day29.adjustInto(februaryDate));
    }
}
