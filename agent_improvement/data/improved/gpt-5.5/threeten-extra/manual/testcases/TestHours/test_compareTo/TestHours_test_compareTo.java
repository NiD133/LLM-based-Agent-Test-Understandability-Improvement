package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_compareTo {

    @Test
    public void test_compareTo() {
        Hours fiveHours = Hours.of(5);
        Hours sixHours = Hours.of(6);

        assertEquals(0, fiveHours.compareTo(fiveHours));
        assertEquals(-1, fiveHours.compareTo(sixHours));
        assertEquals(1, sixHours.compareTo(fiveHours));
    }
}
