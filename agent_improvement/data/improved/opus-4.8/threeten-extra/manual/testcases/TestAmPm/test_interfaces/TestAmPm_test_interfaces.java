package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AmPm} implements the interfaces expected of a date-time enum.
 */
public class TestAmPm_test_interfaces {

    @Test
    public void test_interfaces() {
        // AmPm is an enum, so it must inherit Enum, Serializable and Comparable.
        assertTrue(Enum.class.isAssignableFrom(AmPm.class), "AmPm should be an Enum");
        assertTrue(Serializable.class.isAssignableFrom(AmPm.class), "AmPm should be Serializable");
        assertTrue(Comparable.class.isAssignableFrom(AmPm.class), "AmPm should be Comparable");

        // AmPm participates in the java.time framework, so it must be a TemporalAccessor.
        assertTrue(TemporalAccessor.class.isAssignableFrom(AmPm.class), "AmPm should be a TemporalAccessor");
    }
}
