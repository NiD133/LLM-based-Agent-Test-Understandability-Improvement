package org.threeten.extra;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestWeeks_test_isSerializable {

    @Test
    public void test_isSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Weeks.class));
    }
}
