package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hours#compareTo(Hours)}.
 */
public class TestHours_test_compareTo {

    @Test
    public void test_compareTo() {
        Hours fiveHours = Hours.of(5);
        Hours sixHours = Hours.of(6);

        // an amount compares as equal to itself
        assertEquals(0, fiveHours.compareTo(fiveHours));
        // a smaller amount compares as less than a larger one
        assertEquals(-1, fiveHours.compareTo(sixHours));
        // a larger amount compares as greater than a smaller one
        assertEquals(1, sixHours.compareTo(fiveHours));
    }
}
