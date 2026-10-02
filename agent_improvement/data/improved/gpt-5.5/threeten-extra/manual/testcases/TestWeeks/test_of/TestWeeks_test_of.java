package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_of {

    @Test
    public void test_of() {
        assertEquals(1, Weeks.of(1).getAmount());
        assertEquals(2, Weeks.of(2).getAmount());
        assertEquals(Integer.MAX_VALUE, Weeks.of(Integer.MAX_VALUE).getAmount());

        assertEquals(-1, Weeks.of(-1).getAmount());
        assertEquals(-2, Weeks.of(-2).getAmount());
        assertEquals(Integer.MIN_VALUE, Weeks.of(Integer.MIN_VALUE).getAmount());
    }
}
