package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks} supports Java serialization.
 */
public class TestWeeks_test_isSerializable {

    @Test
    public void weeks_class_implements_Serializable() {
        // Weeks must be serializable so instances can be persisted or transmitted.
        boolean isSerializable = Serializable.class.isAssignableFrom(Weeks.class);

        assertTrue(isSerializable, "Weeks should implement Serializable");
    }
}
