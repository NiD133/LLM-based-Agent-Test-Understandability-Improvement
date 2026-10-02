package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#ofHours(int)} reports numeric overflow.
 */
public class TestSeconds_test_ofHours_overflow {

    /**
     * {@code ofHours} multiplies the hours by 3600 (seconds per hour). When the
     * number of hours is large enough that this multiplication exceeds
     * {@code Integer.MAX_VALUE}, the factory must throw an {@link ArithmeticException}
     * rather than silently overflowing.
     */
    @Test
    public void test_ofHours_overflow() {
        int hoursThatOverflowWhenConvertedToSeconds = (Integer.MAX_VALUE / 3600) + 3600;

        assertThrows(ArithmeticException.class,
                () -> Seconds.ofHours(hoursThatOverflowWhenConvertedToSeconds));
    }
}
