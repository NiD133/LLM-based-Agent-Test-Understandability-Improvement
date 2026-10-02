package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_multipliedBy {

    @Test
    public void test_multipliedBy() {
        Days fiveDays = Days.of(5);

        // Multiplying by zero yields zero days
        assertEquals(Days.of(0), fiveDays.multipliedBy(0));

        // Multiplying by one is an identity operation
        assertEquals(Days.of(5), fiveDays.multipliedBy(1));

        // Multiplying by positive scalars scales the day count
        assertEquals(Days.of(10), fiveDays.multipliedBy(2));
        assertEquals(Days.of(15), fiveDays.multipliedBy(3));

        // Multiplying by a negative scalar negates and scales the day count
        assertEquals(Days.of(-15), fiveDays.multipliedBy(-3));
    }
}
