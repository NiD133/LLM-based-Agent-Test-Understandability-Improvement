package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter} implements the contracts callers rely on.
 */
public class TestQuarter_test_interfaces {

    @Test
    public void test_interfaces() {
        // Quarter is an enum, so it inherits Enum, Serializable and Comparable.
        assertTrue(Enum.class.isAssignableFrom(Quarter.class));
        assertTrue(Serializable.class.isAssignableFrom(Quarter.class));
        assertTrue(Comparable.class.isAssignableFrom(Quarter.class));
        // Quarter also participates in the java.time API as a TemporalAccessor.
        assertTrue(TemporalAccessor.class.isAssignableFrom(Quarter.class));
    }
}
