package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHalf_test_range_null {

    @Test
    public void test_range_null() {
        // Half.range() must reject null field with NullPointerException
        assertThrows(NullPointerException.class, () -> Half.H1.range(null));
    }
}
