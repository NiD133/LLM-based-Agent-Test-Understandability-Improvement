package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Minutes} can be serialized.
 */
public class TestMinutes_test_isSerializable {

    @Test
    public void test_Minutes_implementsSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Minutes.class),
                "Minutes should be assignable to Serializable");
    }
}
