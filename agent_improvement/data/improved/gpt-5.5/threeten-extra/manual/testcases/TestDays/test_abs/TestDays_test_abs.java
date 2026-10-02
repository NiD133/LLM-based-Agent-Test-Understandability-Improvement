package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_abs {

    @Test
    public void test_abs() {
        assertEquals(Days.of(0), Days.of(0).abs());

        assertEquals(Days.of(12), Days.of(12).abs());
        assertEquals(Days.of(12), Days.of(-12).abs());

        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE).abs());
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(-Integer.MAX_VALUE).abs());
    }
}
