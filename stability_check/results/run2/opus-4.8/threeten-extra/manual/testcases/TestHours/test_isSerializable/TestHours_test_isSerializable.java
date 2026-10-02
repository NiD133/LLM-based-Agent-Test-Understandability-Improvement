package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours} supports Java serialization.
 */
public class TestHours_test_isSerializable {

    @Test
    public void test_isSerializable() {
        // Hours must implement Serializable so instances can be serialized.
        assertTrue(Serializable.class.isAssignableFrom(Hours.class));
    }
}
