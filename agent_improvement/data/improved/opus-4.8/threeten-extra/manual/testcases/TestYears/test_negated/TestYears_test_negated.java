package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#negated()}, which flips the sign of the year amount.
 */
public class TestYears_test_negated {

    @Test
    public void test_negated() {
        // Negating zero leaves it unchanged.
        assertEquals(Years.of(0), Years.of(0).negated());

        // A positive amount becomes its negative counterpart.
        assertEquals(Years.of(-12), Years.of(12).negated());

        // A negative amount becomes its positive counterpart.
        assertEquals(Years.of(12), Years.of(-12).negated());

        // The largest positive int negates to its negative counterpart without overflow.
        assertEquals(Years.of(-Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE).negated());
    }
}
