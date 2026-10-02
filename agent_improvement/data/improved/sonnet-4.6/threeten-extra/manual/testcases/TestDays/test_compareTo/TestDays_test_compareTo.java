package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_compareTo {

    @Test
    public void test_compareTo() {
        Days fiveDays = Days.of(5);
        Days sixDays = Days.of(6);

        assertEquals(0, fiveDays.compareTo(fiveDays), "A Days instance compared to itself should return 0");
        assertEquals(-1, fiveDays.compareTo(sixDays), "Five days compared to six days should return negative (less than)");
        assertEquals(1, sixDays.compareTo(fiveDays), "Six days compared to five days should return positive (greater than)");
    }
}
