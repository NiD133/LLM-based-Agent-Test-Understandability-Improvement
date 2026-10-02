package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#compareTo(Weeks)}.
 * <p>
 * {@code compareTo} orders {@code Weeks} by their underlying week count,
 * returning a negative, zero, or positive value when this amount is
 * respectively less than, equal to, or greater than the other amount.
 */
public class TestWeeks_test_compareTo {

    @Test
    public void test_compareTo() {
        Weeks fiveWeeks = Weeks.of(5);
        Weeks sixWeeks = Weeks.of(6);

        // Equal amounts compare as zero.
        assertEquals(0, fiveWeeks.compareTo(fiveWeeks));
        // A smaller amount compares as negative against a larger one.
        assertEquals(-1, fiveWeeks.compareTo(sixWeeks));
        // A larger amount compares as positive against a smaller one.
        assertEquals(1, sixWeeks.compareTo(fiveWeeks));
    }
}
