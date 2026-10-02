package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link UtcInstant} exposes the standard value-type contracts:
 * it must be serializable and naturally comparable to other instants.
 */
public class TestUtcInstant_test_interfaces {

    @Test
    public void implementsSerializableAndComparable() {
        assertTrue(Serializable.class.isAssignableFrom(UtcInstant.class),
                "UtcInstant should implement Serializable");
        assertTrue(Comparable.class.isAssignableFrom(UtcInstant.class),
                "UtcInstant should implement Comparable");
    }
}
