package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Half} implements the interfaces expected of an enum-based
 * temporal type.
 */
public class TestHalf_test_interfaces {

    @Test
    public void test_interfaces() {
        assertTrue(Enum.class.isAssignableFrom(Half.class), "Half should be an enum");
        assertTrue(Serializable.class.isAssignableFrom(Half.class), "Half should be Serializable");
        assertTrue(Comparable.class.isAssignableFrom(Half.class), "Half should be Comparable");
        assertTrue(TemporalAccessor.class.isAssignableFrom(Half.class), "Half should be a TemporalAccessor");
    }
}
