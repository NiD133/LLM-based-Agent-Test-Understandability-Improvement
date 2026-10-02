package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#of(int)}.
 * <p>
 * Each case constructs a {@code Years} instance from a given integer and
 * verifies that {@link Years#getAmount()} returns exactly that integer,
 * including the boundary values {@link Integer#MAX_VALUE} and
 * {@link Integer#MIN_VALUE}.
 */
public class TestYears_test_of {

    @Test
    public void of_storesTheGivenAmount() {
        // typical positive amounts
        assertEquals(1, Years.of(1).getAmount());
        assertEquals(2, Years.of(2).getAmount());
        // largest positive amount
        assertEquals(Integer.MAX_VALUE, Years.of(Integer.MAX_VALUE).getAmount());

        // typical negative amounts
        assertEquals(-1, Years.of(-1).getAmount());
        assertEquals(-2, Years.of(-2).getAmount());
        // smallest negative amount
        assertEquals(Integer.MIN_VALUE, Years.of(Integer.MIN_VALUE).getAmount());
    }
}
