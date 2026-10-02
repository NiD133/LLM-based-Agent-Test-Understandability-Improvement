package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Days#of(int)}.
 *
 * <p>The factory method should store the supplied value verbatim, so the amount
 * returned by {@link Days#getAmount()} must equal the argument passed to
 * {@code Days.of(...)} for every input across the full {@code int} range.
 */
public class TestDays_test_of {

    @Test
    public void of_returnsDaysWithGivenAmount() {
        // Zero and small positive values.
        assertEquals(0, Days.of(0).getAmount());
        assertEquals(1, Days.of(1).getAmount());
        assertEquals(2, Days.of(2).getAmount());

        // Upper boundary of the int range.
        assertEquals(Integer.MAX_VALUE, Days.of(Integer.MAX_VALUE).getAmount());

        // Small negative values.
        assertEquals(-1, Days.of(-1).getAmount());
        assertEquals(-2, Days.of(-2).getAmount());

        // Lower boundary of the int range.
        assertEquals(Integer.MIN_VALUE, Days.of(Integer.MIN_VALUE).getAmount());
    }
}
