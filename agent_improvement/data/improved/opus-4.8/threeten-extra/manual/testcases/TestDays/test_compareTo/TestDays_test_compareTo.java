package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#compareTo(Days)}.
 */
public class TestDays_test_compareTo {

    @Test
    public void test_compareTo() {
        Days fiveDays = Days.of(5);
        Days sixDays = Days.of(6);

        // An amount compared with itself is equal.
        assertEquals(0, fiveDays.compareTo(fiveDays));
        // A smaller amount compares as less than a larger one.
        assertEquals(-1, fiveDays.compareTo(sixDays));
        // A larger amount compares as greater than a smaller one.
        assertEquals(1, sixDays.compareTo(fiveDays));
    }
}
