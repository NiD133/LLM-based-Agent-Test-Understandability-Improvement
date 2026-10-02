package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest_isAsciiLetter {
    @Test
    void isAsciiLetter() {
        assertAsciiLetter('a');
        assertAsciiLetter('n');
        assertAsciiLetter('z');
        assertAsciiLetter('A');
        assertAsciiLetter('N');
        assertAsciiLetter('Z');

        assertNotAsciiLetter(' ');
        assertNotAsciiLetter('-');
        assertNotAsciiLetter('0');
        assertNotAsciiLetter('ß');
        assertNotAsciiLetter('Ě');
    }

    private static void assertAsciiLetter(char candidate) {
        assertTrue(StringUtil.isAsciiLetter(candidate));
    }

    private static void assertNotAsciiLetter(char candidate) {
        assertFalse(StringUtil.isAsciiLetter(candidate));
    }
}
