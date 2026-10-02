package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half} implements the interfaces expected of a
 * date-time enum.
 */
public class TestHalf_test_interfaces {

    @Test
    public void test_interfaces() {
        // Half is declared as an enum, so it is a subtype of Enum.
        assertTrue(Enum.class.isAssignableFrom(Half.class));
        // Enums are serializable and comparable by definition.
        assertTrue(Serializable.class.isAssignableFrom(Half.class));
        assertTrue(Comparable.class.isAssignableFrom(Half.class));
        // Half also participates in the java.time API as a temporal value.
        assertTrue(TemporalAccessor.class.isAssignableFrom(Half.class));
    }
}
