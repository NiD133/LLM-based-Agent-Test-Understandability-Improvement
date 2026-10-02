package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_compareTo_nullDayOfMonth {

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        // DayOfMonth.now() should match the day-of-month of the current local date.
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        // DayOfMonth.now(zone) should match the day-of-month of the current date in that zone.
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // compareTo(DayOfMonth)
    //-----------------------------------------------------------------------
    @Test
    public void test_compareTo_nullDayOfMonth() {
        // Comparing against a null DayOfMonth must throw NullPointerException.
        DayOfMonth test = DayOfMonth.of(1);
        DayOfMonth nullDayOfMonth = null;
        assertThrows(NullPointerException.class, () -> test.compareTo(nullDayOfMonth));
    }
}
