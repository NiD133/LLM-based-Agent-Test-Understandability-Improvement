package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Weeks#of(int)} factory method.
 * <p>
 * Each case verifies that the {@code Weeks} instance created from a given
 * number of weeks reports that same number back via {@link Weeks#getAmount()}.
 */
public class TestWeeks_test_of {

    @Test
    public void of_storesTheGivenNumberOfWeeks() {
        // Positive amounts, including the largest representable value.
        assertEquals(1, Weeks.of(1).getAmount());
        assertEquals(2, Weeks.of(2).getAmount());
        assertEquals(Integer.MAX_VALUE, Weeks.of(Integer.MAX_VALUE).getAmount());

        // Negative amounts, including the smallest representable value.
        assertEquals(-1, Weeks.of(-1).getAmount());
        assertEquals(-2, Weeks.of(-2).getAmount());
        assertEquals(Integer.MIN_VALUE, Weeks.of(Integer.MIN_VALUE).getAmount());
    }
}
