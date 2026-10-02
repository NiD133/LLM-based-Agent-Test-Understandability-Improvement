package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Days} supports Java serialization.
 */
public class TestDays_test_isSerializable {

    @Test
    public void days_class_implements_Serializable() {
        assertTrue(
                Serializable.class.isAssignableFrom(Days.class),
                "Days should be serializable");
    }
}
