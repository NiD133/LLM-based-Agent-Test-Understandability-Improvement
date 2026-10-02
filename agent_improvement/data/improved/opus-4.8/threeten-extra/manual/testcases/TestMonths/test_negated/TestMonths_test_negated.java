package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#negated()}, which returns a copy of the amount with its sign flipped.
 */
public class TestMonths_test_negated {

    @Test
    public void test_negated() {
        // Negating zero stays zero.
        assertEquals(Months.of(0), Months.of(0).negated());

        // A positive amount becomes the matching negative amount.
        assertEquals(Months.of(-12), Months.of(12).negated());

        // A negative amount becomes the matching positive amount.
        assertEquals(Months.of(12), Months.of(-12).negated());

        // The largest positive int negates to its negative counterpart without overflow.
        assertEquals(Months.of(-Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE).negated());
    }
}
