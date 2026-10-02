package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours} can be serialized, i.e. it implements
 * {@link java.io.Serializable}.
 */
public class TestHours_test_isSerializable {

    @Test
    public void hours_class_implements_Serializable() {
        assertTrue(Serializable.class.isAssignableFrom(Hours.class),
                "Hours should implement Serializable");
    }
}
