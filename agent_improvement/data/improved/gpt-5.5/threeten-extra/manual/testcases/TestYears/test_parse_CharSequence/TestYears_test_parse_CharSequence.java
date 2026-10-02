package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_parse_CharSequence {

    @Test
    public void test_parse_CharSequence() {
        assertEquals(Years.of(0), Years.parse("P0Y"));
        assertEquals(Years.of(1), Years.parse("P1Y"));
        assertEquals(Years.of(2), Years.parse("P2Y"));
        assertEquals(Years.of(123456789), Years.parse("P123456789Y"));

        assertEquals(Years.of(-2), Years.parse("P-2Y"));
        assertEquals(Years.of(-2), Years.parse("-P2Y"));
        assertEquals(Years.of(2), Years.parse("-P-2Y"));
    }
}
