package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#negated()}, which returns a copy of the amount with its sign flipped.
 */
public class TestMinutes_test_negated {

    @Test
    public void test_negated() {
        // Negating zero leaves it unchanged.
        assertEquals(Minutes.of(0), Minutes.of(0).negated());
        // A positive amount becomes the matching negative amount.
        assertEquals(Minutes.of(-12), Minutes.of(12).negated());
        // A negative amount becomes the matching positive amount.
        assertEquals(Minutes.of(12), Minutes.of(-12).negated());
        // The largest int value negates to its negative counterpart without overflow.
        assertEquals(Minutes.of(-Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE).negated());
    }
}
