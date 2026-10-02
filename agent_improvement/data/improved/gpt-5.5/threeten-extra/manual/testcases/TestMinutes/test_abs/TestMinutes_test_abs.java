package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_abs {

    @Test
    public void test_abs() {
        assertAbsoluteMinutes(0, 0);
        assertAbsoluteMinutes(12, 12);
        assertAbsoluteMinutes(12, -12);
        assertAbsoluteMinutes(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertAbsoluteMinutes(Integer.MAX_VALUE, -Integer.MAX_VALUE);
    }

    private void assertAbsoluteMinutes(int expectedMinutes, int inputMinutes) {
        assertEquals(Minutes.of(expectedMinutes), Minutes.of(inputMinutes).abs());
    }
}
