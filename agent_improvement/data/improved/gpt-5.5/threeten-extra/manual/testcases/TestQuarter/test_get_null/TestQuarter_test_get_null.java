package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_get_null {

    @Test
    public void test_get_null() {
        assertThrows(NullPointerException.class, () -> Quarter.Q2.get(null));
    }
}
