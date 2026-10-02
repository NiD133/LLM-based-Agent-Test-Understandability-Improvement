package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#compareTo(Minutes)}.
 */
public class TestMinutes_test_compareTo {

    @Test
    public void test_compareTo() {
        Minutes fiveMinutes = Minutes.of(5);
        Minutes sixMinutes = Minutes.of(6);

        // Comparing an amount with itself yields zero.
        assertEquals(0, fiveMinutes.compareTo(fiveMinutes));
        // A smaller amount compares as less than a larger amount.
        assertEquals(-1, fiveMinutes.compareTo(sixMinutes));
        // A larger amount compares as greater than a smaller amount.
        assertEquals(1, sixMinutes.compareTo(fiveMinutes));
    }
}
