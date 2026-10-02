package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#negated()}, which returns a copy of the amount with its sign flipped.
 */
public class TestWeeks_test_negated {

    @Test
    public void test_negated() {
        // Negating zero yields zero.
        assertEquals(Weeks.of(0), Weeks.of(0).negated());

        // A positive amount becomes the equivalent negative amount.
        assertEquals(Weeks.of(-12), Weeks.of(12).negated());

        // A negative amount becomes the equivalent positive amount.
        assertEquals(Weeks.of(12), Weeks.of(-12).negated());

        // The largest positive int negates without overflow.
        assertEquals(Weeks.of(-Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE).negated());
    }
}
