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
 * Tests the factory and interface contracts of {@link DayOfMonth}.
 */
public class TestDayOfMonth_test_interfaces {

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        // DayOfMonth.now() should match the day-of-month of today's date.
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        // DayOfMonth.now(zone) should match the day-of-month of today's date in that zone.
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // implemented interfaces
    //-----------------------------------------------------------------------
    @Test
    public void test_interfaces() {
        // DayOfMonth is declared to implement these four interfaces.
        assertTrue(Serializable.class.isAssignableFrom(DayOfMonth.class));
        assertTrue(Comparable.class.isAssignableFrom(DayOfMonth.class));
        assertTrue(TemporalAdjuster.class.isAssignableFrom(DayOfMonth.class));
        assertTrue(TemporalAccessor.class.isAssignableFrom(DayOfMonth.class));
    }
}
