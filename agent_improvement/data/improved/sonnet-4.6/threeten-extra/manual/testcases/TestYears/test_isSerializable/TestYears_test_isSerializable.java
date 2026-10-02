package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

public class TestYears_test_isSerializable {

    /**
     * Verifies that {@link Years} declares {@link Serializable} in its type hierarchy,
     * ensuring instances can be safely serialized and deserialized.
     */
    @Test
    public void test_isSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Years.class));
    }
}
