package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfMonth#atMonth(int)}, focusing on day 31 (the maximum possible
 * day-of-month). For each month, {@code atMonth} must clamp day 31 down to that
 * month's last valid day.
 */
public class TestDayOfMonth_test_atMonth_int_31 {

    //-----------------------------------------------------------------------
    // now() reflects the current day-of-month from the system clock.
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // atMonth(int): day 31 is clamped to each month's last valid day.
    //-----------------------------------------------------------------------
    @Test
    public void test_atMonth_int_31() {
        DayOfMonth day31 = DayOfMonth.of(31);

        // Months with 31 days keep day 31; shorter months clamp down.
        assertEquals(MonthDay.of(1, 31), day31.atMonth(1));   // January   - 31 days
        assertEquals(MonthDay.of(2, 29), day31.atMonth(2));   // February  - clamps to 29
        assertEquals(MonthDay.of(3, 31), day31.atMonth(3));   // March     - 31 days
        assertEquals(MonthDay.of(4, 30), day31.atMonth(4));   // April     - clamps to 30
        assertEquals(MonthDay.of(5, 31), day31.atMonth(5));   // May       - 31 days
        assertEquals(MonthDay.of(6, 30), day31.atMonth(6));   // June      - clamps to 30
        assertEquals(MonthDay.of(7, 31), day31.atMonth(7));   // July      - 31 days
        assertEquals(MonthDay.of(8, 31), day31.atMonth(8));   // August    - 31 days
        assertEquals(MonthDay.of(9, 30), day31.atMonth(9));   // September - clamps to 30
        assertEquals(MonthDay.of(10, 31), day31.atMonth(10)); // October   - 31 days
        assertEquals(MonthDay.of(11, 30), day31.atMonth(11)); // November  - clamps to 30
        assertEquals(MonthDay.of(12, 31), day31.atMonth(12)); // December  - 31 days
    }
}
