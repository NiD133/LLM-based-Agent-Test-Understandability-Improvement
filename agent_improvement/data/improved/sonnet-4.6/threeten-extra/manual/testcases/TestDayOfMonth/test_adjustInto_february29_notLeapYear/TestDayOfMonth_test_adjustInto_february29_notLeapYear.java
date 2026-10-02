package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_adjustInto_february29_notLeapYear {

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    @Test
    public void test_adjustInto_february29_notLeapYear() {
        // February 2007 is not a leap year, so day 29 does not exist
        LocalDate februaryInNonLeapYear = LocalDate.of(2007, 2, 1);
        DayOfMonth day29 = DayOfMonth.of(29);
        assertThrows(DateTimeException.class, () -> day29.adjustInto(februaryInNonLeapYear));
    }
}
