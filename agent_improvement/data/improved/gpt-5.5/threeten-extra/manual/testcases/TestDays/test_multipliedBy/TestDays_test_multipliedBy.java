package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_multipliedBy {

    @Test
    public void test_multipliedBy() {
        Days fiveDays = Days.of(5);

        assertEquals(Days.of(0), fiveDays.multipliedBy(0));
        assertEquals(Days.of(5), fiveDays.multipliedBy(1));
        assertEquals(Days.of(10), fiveDays.multipliedBy(2));
        assertEquals(Days.of(15), fiveDays.multipliedBy(3));
        assertEquals(Days.of(-15), fiveDays.multipliedBy(-3));
    }
}
