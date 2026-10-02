package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days} implements {@link Serializable}, which is required
 * for the class to participate in Java object serialization.
 */
public class TestDays_test_isSerializable {

    @Test
    public void test_isSerializable() {
        // Days must implement Serializable so instances can be serialized and deserialized
        assertTrue(Serializable.class.isAssignableFrom(Days.class));
    }
}
