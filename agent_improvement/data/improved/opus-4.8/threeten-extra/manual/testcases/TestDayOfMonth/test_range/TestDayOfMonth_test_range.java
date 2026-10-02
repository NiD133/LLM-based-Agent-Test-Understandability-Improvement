package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth} covering the current-day factories and the
 * {@code range} query.
 */
public class TestDayOfMonth_test_range {

    /** A fixed sample instance: the 12th day of the month. */
    private static final DayOfMonth TEST = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        // DayOfMonth.now() must agree with the day-of-month of today's date.
        int expectedDayOfMonth = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        // DayOfMonth.now(zone) must agree with today's date in the same zone.
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        int expectedDayOfMonth = LocalDate.now(zone).getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    // range(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void test_range() {
        // The range reported for DAY_OF_MONTH is the field's own standard range.
        assertEquals(DAY_OF_MONTH.range(), TEST.range(DAY_OF_MONTH));
    }
}
