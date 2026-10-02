package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Weeks#parse(CharSequence)} correctly converts ISO-8601
 * {@code PnW} period strings into the matching {@code Weeks} value.
 */
public class TestWeeks_test_parse_CharSequence {

    @Test
    public void parse_returnsWeeksMatchingTheTextAmount() {
        // Plain, non-negative amounts.
        assertEquals(Weeks.of(0), Weeks.parse("P0W"));
        assertEquals(Weeks.of(1), Weeks.parse("P1W"));
        assertEquals(Weeks.of(2), Weeks.parse("P2W"));
        assertEquals(Weeks.of(123456789), Weeks.parse("P123456789W"));

        // The week amount itself may carry a minus sign.
        assertEquals(Weeks.of(-2), Weeks.parse("P-2W"));

        // A leading minus sign negates the whole amount.
        assertEquals(Weeks.of(-2), Weeks.parse("-P2W"));

        // Both signs combine, so the two negations cancel out.
        assertEquals(Weeks.of(2), Weeks.parse("-P-2W"));
    }
}
