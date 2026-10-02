package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_TemporalAmount_Days {

    @Test
    public void test_plus_TemporalAmount_Days() {
        Days fiveDays = Days.of(5);

        // Adding zero days leaves the value unchanged
        assertEquals(Days.of(5), fiveDays.plus(Days.of(0)));

        // Adding a positive amount increases the day count
        assertEquals(Days.of(7), fiveDays.plus(Days.of(2)));

        // Adding a negative amount decreases the day count
        assertEquals(Days.of(3), fiveDays.plus(Days.of(-2)));

        // Adding near the upper boundary does not overflow
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE - 1).plus(Days.of(1)));

        // Adding near the lower boundary does not overflow
        assertEquals(Days.of(Integer.MIN_VALUE), Days.of(Integer.MIN_VALUE + 1).plus(Days.of(-1)));
    }
}
