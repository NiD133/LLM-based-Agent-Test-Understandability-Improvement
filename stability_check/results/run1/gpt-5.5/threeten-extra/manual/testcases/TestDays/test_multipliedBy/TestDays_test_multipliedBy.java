package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_multipliedBy {

    private static final Days FIVE_DAYS = Days.of(5);

    @Test
    public void test_multipliedBy() {
        assertEquals(Days.of(0), FIVE_DAYS.multipliedBy(0));
        assertEquals(Days.of(5), FIVE_DAYS.multipliedBy(1));
        assertEquals(Days.of(10), FIVE_DAYS.multipliedBy(2));
        assertEquals(Days.of(15), FIVE_DAYS.multipliedBy(3));
        assertEquals(Days.of(-15), FIVE_DAYS.multipliedBy(-3));
    }
}
