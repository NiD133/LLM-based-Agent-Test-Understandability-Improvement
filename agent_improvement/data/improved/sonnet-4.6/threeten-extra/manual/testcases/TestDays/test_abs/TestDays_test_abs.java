package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_abs {

    @Test
    public void test_abs() {
        // Zero stays zero
        assertEquals(Days.of(0), Days.of(0).abs());

        // Positive values are unchanged
        assertEquals(Days.of(12), Days.of(12).abs());
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE).abs());

        // Negative values become their positive counterpart
        assertEquals(Days.of(12), Days.of(-12).abs());
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(-Integer.MAX_VALUE).abs());
    }
}
