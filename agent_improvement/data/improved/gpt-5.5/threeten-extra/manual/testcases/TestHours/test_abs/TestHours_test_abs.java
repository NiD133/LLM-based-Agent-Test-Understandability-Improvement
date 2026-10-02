package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_abs {

    @Test
    public void test_abs() {
        assertAbsoluteHours(0, 0);
        assertAbsoluteHours(12, 12);
        assertAbsoluteHours(-12, 12);
        assertAbsoluteHours(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertAbsoluteHours(-Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    private void assertAbsoluteHours(int inputHours, int expectedHours) {
        assertEquals(Hours.of(expectedHours), Hours.of(inputHours).abs());
    }
}
