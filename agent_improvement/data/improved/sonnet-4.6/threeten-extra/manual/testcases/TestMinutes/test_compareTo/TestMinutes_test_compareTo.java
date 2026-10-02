package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_compareTo {

    @Test
    public void test_compareTo() {
        Minutes fewer = Minutes.of(5);
        Minutes more = Minutes.of(6);
        assertEquals(0, fewer.compareTo(fewer), "Same instance should compare equal to itself");
        assertEquals(-1, fewer.compareTo(more), "Smaller amount should compare less than larger");
        assertEquals(1, more.compareTo(fewer), "Larger amount should compare greater than smaller");
    }
}
