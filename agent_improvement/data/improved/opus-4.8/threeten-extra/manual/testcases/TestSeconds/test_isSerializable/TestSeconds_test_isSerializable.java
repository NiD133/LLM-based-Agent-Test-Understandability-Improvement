package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds} can be serialized, which requires the class
 * to implement {@link Serializable}.
 */
public class TestSeconds_test_isSerializable {

    @Test
    public void test_isSerializable() {
        // Seconds must implement Serializable so its instances can be serialized.
        assertTrue(Serializable.class.isAssignableFrom(Seconds.class));
    }
}
