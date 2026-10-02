package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_abs {

    @Test
    public void test_abs() {
        assertEquals(Seconds.of(0), Seconds.of(0).abs());

        assertEquals(Seconds.of(12), Seconds.of(12).abs());
        assertEquals(Seconds.of(12), Seconds.of(-12).abs());

        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE).abs());
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(-Integer.MAX_VALUE).abs());
    }
}
