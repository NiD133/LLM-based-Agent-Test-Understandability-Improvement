package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_compareTo {

    @Test
    public void test_compareTo() {
        Days fiveDays = Days.of(5);
        Days sixDays = Days.of(6);

        assertEquals(0, fiveDays.compareTo(fiveDays));
        assertEquals(-1, fiveDays.compareTo(sixDays));
        assertEquals(1, sixDays.compareTo(fiveDays));
    }
}
