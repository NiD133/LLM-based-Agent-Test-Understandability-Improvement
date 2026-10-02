package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

public class TestMonths_test_isSerializable {

    @Test
    public void test_isSerializable() {
        // Months must implement Serializable so instances can be safely persisted and transferred
        assertTrue(Serializable.class.isAssignableFrom(Months.class));
    }
}
