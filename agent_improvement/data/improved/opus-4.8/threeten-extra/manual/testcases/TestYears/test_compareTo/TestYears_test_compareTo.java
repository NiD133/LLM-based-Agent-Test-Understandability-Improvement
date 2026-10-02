package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#compareTo(Years)}.
 */
public class TestYears_test_compareTo {

    @Test
    public void test_compareTo() {
        Years fiveYears = Years.of(5);
        Years sixYears = Years.of(6);

        // Comparing an amount to itself yields zero (equal).
        assertEquals(0, fiveYears.compareTo(fiveYears));
        // A smaller amount compares as negative against a larger one.
        assertEquals(-1, fiveYears.compareTo(sixYears));
        // A larger amount compares as positive against a smaller one.
        assertEquals(1, sixYears.compareTo(fiveYears));
    }
}
