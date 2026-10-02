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

public class TestDayOfYear_test_interfaces {

    // -----------------------------------------------------------------------
    // DayOfYear.now() should return the day-of-year matching the current date
    // in the default time-zone. The test is retried to avoid rare midnight rollover failures.
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    // -----------------------------------------------------------------------
    // DayOfYear.now(ZoneId) should honour the supplied zone, which may differ
    // from the JVM default (e.g. Asia/Tokyo is UTC+9 and can be a different
    // calendar day than the local machine).
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    // -----------------------------------------------------------------------
    // DayOfYear must implement the four interfaces required by its public contract:
    //   Serializable      – allows safe storage/transmission of instances
    //   Comparable        – enables natural ordering of day-of-year values
    //   TemporalAdjuster  – lets DayOfYear adjust any Temporal (e.g. LocalDate)
    //   TemporalAccessor  – lets DayOfYear be read by temporal field queries
    @Test
    public void test_interfaces() {
        assertTrue(Serializable.class.isAssignableFrom(DayOfYear.class));
        assertTrue(Comparable.class.isAssignableFrom(DayOfYear.class));
        assertTrue(TemporalAdjuster.class.isAssignableFrom(DayOfYear.class));
        assertTrue(TemporalAccessor.class.isAssignableFrom(DayOfYear.class));
    }
}
