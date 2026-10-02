package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AmPm} satisfies the type contracts required by its design:
 * it must be an enum (providing singleton semantics and ordinal ordering),
 * serializable (so it survives cross-JVM transport), comparable (supporting natural
 * ordering of AM before PM), and a {@link TemporalAccessor} (integrating with the
 * java.time query API).
 */
public class TestAmPm_test_interfaces {

    @Test
    public void test_interfaces() {
        // AmPm is declared as an enum, which implicitly extends java.lang.Enum
        assertTrue(Enum.class.isAssignableFrom(AmPm.class),
                "AmPm must be an Enum");

        // Enums are automatically Serializable, confirming round-trip persistence
        assertTrue(Serializable.class.isAssignableFrom(AmPm.class),
                "AmPm must implement Serializable");

        // Enums are automatically Comparable (ordered by declaration: AM=0, PM=1)
        assertTrue(Comparable.class.isAssignableFrom(AmPm.class),
                "AmPm must implement Comparable");

        // AmPm explicitly implements TemporalAccessor to plug into java.time queries
        assertTrue(TemporalAccessor.class.isAssignableFrom(AmPm.class),
                "AmPm must implement TemporalAccessor");
    }
}
