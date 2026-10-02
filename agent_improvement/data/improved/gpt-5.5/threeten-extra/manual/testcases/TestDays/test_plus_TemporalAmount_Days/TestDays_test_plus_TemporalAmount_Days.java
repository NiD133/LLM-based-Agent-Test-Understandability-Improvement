package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_TemporalAmount_Days {

    @Test
    public void test_plus_TemporalAmount_Days() {
        Days test5 = Days.of(5);

        assertEquals(Days.of(5), test5.plus(Days.of(0)));
        assertEquals(Days.of(7), test5.plus(Days.of(2)));
        assertEquals(Days.of(3), test5.plus(Days.of(-2)));

        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE - 1).plus(Days.of(1)));
        assertEquals(Days.of(Integer.MIN_VALUE), Days.of(Integer.MIN_VALUE + 1).plus(Days.of(-1)));
    }
}
