package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests that {@link DayOfMonth} correctly implements its declared interfaces
 * ({@link Serializable}, {@link Comparable}, {@link TemporalAdjuster}, {@link TemporalAccessor})
 * and that {@link DayOfMonth#now()} / {@link DayOfMonth#now(ZoneId)} reflect the current date.
 */
public class TestDayOfMonth_test_interfaces {

    // -----------------------------------------------------------------------
    // now() — uses the system default zone
    // -----------------------------------------------------------------------

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    // -----------------------------------------------------------------------
    // now(ZoneId) — uses the supplied zone
    // -----------------------------------------------------------------------

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    // -----------------------------------------------------------------------
    // Interface checks — DayOfMonth must be assignable to each required type
    // -----------------------------------------------------------------------

    @Test
    public void test_interfaces() {
        assertTrue(Serializable.class.isAssignableFrom(DayOfMonth.class),
                "DayOfMonth must implement Serializable");
        assertTrue(Comparable.class.isAssignableFrom(DayOfMonth.class),
                "DayOfMonth must implement Comparable");
        assertTrue(TemporalAdjuster.class.isAssignableFrom(DayOfMonth.class),
                "DayOfMonth must implement TemporalAdjuster");
        assertTrue(TemporalAccessor.class.isAssignableFrom(DayOfMonth.class),
                "DayOfMonth must implement TemporalAccessor");
    }
}
