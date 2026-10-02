package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_isSerializable {

    @Test
    public void test_isSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Weeks.class));
    }
}
