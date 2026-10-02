package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_of {

    @Test
    public void test_of() {
        assertEquals(0, Hours.of(0).getAmount());

        assertEquals(1, Hours.of(1).getAmount());
        assertEquals(2, Hours.of(2).getAmount());
        assertEquals(Integer.MAX_VALUE, Hours.of(Integer.MAX_VALUE).getAmount());

        assertEquals(-1, Hours.of(-1).getAmount());
        assertEquals(-2, Hours.of(-2).getAmount());
        assertEquals(Integer.MIN_VALUE, Hours.of(Integer.MIN_VALUE).getAmount());
    }
}
