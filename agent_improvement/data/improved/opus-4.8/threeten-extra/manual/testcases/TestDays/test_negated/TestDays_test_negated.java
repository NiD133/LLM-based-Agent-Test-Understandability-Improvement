package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#negated()}.
 * <p>
 * Negating a {@code Days} amount flips the sign of its value while leaving
 * the magnitude unchanged; zero negates to itself.
 */
public class TestDays_test_negated {

    @Test
    public void test_negated() {
        // Zero is unaffected by negation.
        assertEquals(Days.of(0), Days.of(0).negated());

        // A positive amount becomes the equivalent negative amount.
        assertEquals(Days.of(-12), Days.of(12).negated());

        // A negative amount becomes the equivalent positive amount.
        assertEquals(Days.of(12), Days.of(-12).negated());

        // The largest positive amount negates to its negative counterpart.
        assertEquals(Days.of(-Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE).negated());
    }
}
