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
 * Tests the factory "now" methods and the interfaces implemented by {@link DayOfYear}.
 */
public class TestDayOfYear_test_interfaces {

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_usesSystemDefaultZone() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_usesGivenZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // implemented interfaces
    //-----------------------------------------------------------------------
    @Test
    public void implementsExpectedInterfaces() {
        assertTrue(Serializable.class.isAssignableFrom(DayOfYear.class));
        assertTrue(Comparable.class.isAssignableFrom(DayOfYear.class));
        assertTrue(TemporalAdjuster.class.isAssignableFrom(DayOfYear.class));
        assertTrue(TemporalAccessor.class.isAssignableFrom(DayOfYear.class));
    }
}
