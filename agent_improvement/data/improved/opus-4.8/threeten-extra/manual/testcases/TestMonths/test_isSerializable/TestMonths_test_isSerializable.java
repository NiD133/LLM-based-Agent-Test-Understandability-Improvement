package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months} can be serialized.
 */
public class TestMonths_test_isSerializable {

    @Test
    public void test_isSerializable() {
        // Months must implement Serializable so that instances can be persisted or transmitted.
        assertTrue(Serializable.class.isAssignableFrom(Months.class));
    }
}
