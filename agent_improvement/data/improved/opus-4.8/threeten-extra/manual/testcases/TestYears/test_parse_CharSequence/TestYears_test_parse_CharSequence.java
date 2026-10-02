package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#parse(CharSequence)} converts ISO-8601 "PnY" period
 * strings into the matching {@link Years} amount.
 */
public class TestYears_test_parse_CharSequence {

    @Test
    public void test_parse_CharSequence() {
        // Plain, non-negative amounts.
        assertEquals(Years.of(0), Years.parse("P0Y"));
        assertEquals(Years.of(1), Years.parse("P1Y"));
        assertEquals(Years.of(2), Years.parse("P2Y"));

        // A large value parses without overflow.
        assertEquals(Years.of(123456789), Years.parse("P123456789Y"));

        // Negative results can be expressed by signing the number, the period,
        // or both (two negatives cancel out back to a positive amount).
        assertEquals(Years.of(-2), Years.parse("P-2Y"));
        assertEquals(Years.of(-2), Years.parse("-P2Y"));
        assertEquals(Years.of(2), Years.parse("-P-2Y"));
    }
}
