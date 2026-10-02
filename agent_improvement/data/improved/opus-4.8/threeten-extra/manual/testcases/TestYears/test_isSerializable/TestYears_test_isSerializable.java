package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years} supports Java serialization.
 */
public class TestYears_test_isSerializable {

    @Test
    public void years_implementsSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Years.class),
                "Years should implement Serializable");
    }
}
