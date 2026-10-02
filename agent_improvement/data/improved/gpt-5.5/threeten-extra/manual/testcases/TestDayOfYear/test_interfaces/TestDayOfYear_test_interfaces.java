package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;

import org.junitpioneer.jupiter.RetryingTest;
import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_interfaces {

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    @Test
    public void test_interfaces() {
        assertImplementedInterface(Serializable.class);
        assertImplementedInterface(Comparable.class);
        assertImplementedInterface(TemporalAdjuster.class);
        assertImplementedInterface(TemporalAccessor.class);
    }

    private static void assertImplementedInterface(Class<?> expectedInterface) {
        assertTrue(expectedInterface.isAssignableFrom(DayOfYear.class));
    }
}
