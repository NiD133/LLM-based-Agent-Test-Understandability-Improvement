package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_compareTo {

    @Test
    public void test_compareTo() {
        Minutes fiveMinutes = Minutes.of(5);
        Minutes sixMinutes = Minutes.of(6);

        assertEquals(0, fiveMinutes.compareTo(fiveMinutes));
        assertEquals(-1, fiveMinutes.compareTo(sixMinutes));
        assertEquals(1, sixMinutes.compareTo(fiveMinutes));
    }
}
