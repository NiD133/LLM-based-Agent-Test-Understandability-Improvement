package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_int {

    @Test
    public void test_plus_int() {
        Days fiveDays = Days.of(5);

        assertEquals(Days.of(5), fiveDays.plus(0));
        assertEquals(Days.of(7), fiveDays.plus(2));
        assertEquals(Days.of(3), fiveDays.plus(-2));

        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Days.of(Integer.MIN_VALUE), Days.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
