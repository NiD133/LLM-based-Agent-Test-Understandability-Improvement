package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth} covering the "now" factory methods and the
 * null-handling contract of {@link DayOfMonth#range(TemporalField)}.
 */
public class TestDayOfMonth_test_range_null {

    /** A fixed day-of-month instance used as the subject under test. */
    private static final DayOfMonth TEST = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        // DayOfMonth.now() must agree with the day-of-month of today's local date.
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        // DayOfMonth.now(zone) must agree with the day-of-month of today's date in that zone.
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // range(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void test_range_null() {
        // Passing a null field must be rejected with a NullPointerException.
        assertThrows(NullPointerException.class, () -> TEST.range((TemporalField) null));
    }
}
