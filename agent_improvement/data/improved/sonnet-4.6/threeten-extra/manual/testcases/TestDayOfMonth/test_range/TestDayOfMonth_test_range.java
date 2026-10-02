package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_range {

    // The DayOfMonth instance used in field-range assertions
    private static final DayOfMonth TEST = DayOfMonth.of(12);

    // -----------------------------------------------------------------------
    // now() returns the current day-of-month in the default time-zone
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    // -----------------------------------------------------------------------
    // now(ZoneId) returns the current day-of-month for the given time-zone
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    // -----------------------------------------------------------------------
    // range(DAY_OF_MONTH) should return the same ValueRange as the field itself reports
    @Test
    public void test_range() {
        assertEquals(DAY_OF_MONTH.range(), TEST.range(DAY_OF_MONTH));
    }
}
