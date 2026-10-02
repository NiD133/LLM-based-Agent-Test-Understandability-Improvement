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

public class TestDayOfMonth_test_interfaces {

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
    public void test_interfaces() {
        assertTrue(Serializable.class.isAssignableFrom(DayOfMonth.class));
        assertTrue(Comparable.class.isAssignableFrom(DayOfMonth.class));
        assertTrue(TemporalAdjuster.class.isAssignableFrom(DayOfMonth.class));
        assertTrue(TemporalAccessor.class.isAssignableFrom(DayOfMonth.class));
    }
}
