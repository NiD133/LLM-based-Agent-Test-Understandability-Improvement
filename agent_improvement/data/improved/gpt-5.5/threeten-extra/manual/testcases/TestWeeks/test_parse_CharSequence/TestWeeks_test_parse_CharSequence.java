package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_parse_CharSequence {

    @Test
    public void test_parse_CharSequence() {
        assertParsesToWeeks(0, "P0W");
        assertParsesToWeeks(1, "P1W");
        assertParsesToWeeks(2, "P2W");
        assertParsesToWeeks(123456789, "P123456789W");
        assertParsesToWeeks(-2, "P-2W");
        assertParsesToWeeks(-2, "-P2W");
        assertParsesToWeeks(2, "-P-2W");
    }

    private static void assertParsesToWeeks(int expectedWeeks, String text) {
        assertEquals(Weeks.of(expectedWeeks), Weeks.parse(text));
    }
}
