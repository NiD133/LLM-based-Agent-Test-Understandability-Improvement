package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_multipliedBy {

    @Test
    public void test_multipliedBy() {
        Hours fiveHours = Hours.of(5);

        assertEquals(Hours.of(0),   fiveHours.multipliedBy(0));
        assertEquals(Hours.of(5),   fiveHours.multipliedBy(1));
        assertEquals(Hours.of(10),  fiveHours.multipliedBy(2));
        assertEquals(Hours.of(15),  fiveHours.multipliedBy(3));
        assertEquals(Hours.of(-15), fiveHours.multipliedBy(-3));
    }
}
