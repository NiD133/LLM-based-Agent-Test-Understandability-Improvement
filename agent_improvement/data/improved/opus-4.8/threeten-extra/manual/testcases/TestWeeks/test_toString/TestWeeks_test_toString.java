package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#toString()} renders the amount in the
 * ISO-8601 {@code PnW} format, including the negative case.
 */
public class TestWeeks_test_toString {

    @Test
    public void test_toString() {
        // Positive amount: 5 weeks renders as "P5W".
        Weeks fiveWeeks = Weeks.of(5);
        assertEquals("P5W", fiveWeeks.toString());

        // Negative amount: -1 week renders as "P-1W".
        Weeks minusOneWeek = Weeks.of(-1);
        assertEquals("P-1W", minusOneWeek.toString());
    }
}
