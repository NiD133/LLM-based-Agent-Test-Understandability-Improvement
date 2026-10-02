package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CharSetTest_testGetInstance extends AbstractLangTest {

    @Test
    void testGetInstance() {
        assertSame(CharSet.EMPTY, CharSet.getInstance((String) null));
        assertSame(CharSet.EMPTY, CharSet.getInstance((String[]) null));
        assertSame(CharSet.EMPTY, CharSet.getInstance(null));
        assertSame(CharSet.EMPTY, CharSet.getInstance(""));
        assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("a-zA-Z"));
        assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("A-Za-z"));
        assertSame(CharSet.ASCII_ALPHA_LOWER, CharSet.getInstance("a-z"));
        assertSame(CharSet.ASCII_ALPHA_UPPER, CharSet.getInstance("A-Z"));
        assertSame(CharSet.ASCII_NUMERIC, CharSet.getInstance("0-9"));
    }

    @Nested
    @DisplayName("getInstance returns EMPTY for null and empty inputs")
    class WhenInputIsNullOrEmpty {

        @Test
        @DisplayName("null String argument returns EMPTY singleton")
        void nullStringReturnsEmpty() {
            assertSame(CharSet.EMPTY, CharSet.getInstance((String) null),
                "A null String argument should return the EMPTY singleton");
        }

        @Test
        @DisplayName("null String[] argument returns EMPTY singleton")
        void nullStringArrayReturnsEmpty() {
            assertSame(CharSet.EMPTY, CharSet.getInstance((String[]) null),
                "A null String[] argument should return the EMPTY singleton");
        }

        @Test
        @DisplayName("uncast null argument returns EMPTY singleton")
        void uncastNullReturnsEmpty() {
            assertSame(CharSet.EMPTY, CharSet.getInstance(null),
                "An uncast null argument should return the EMPTY singleton");
        }

        @Test
        @DisplayName("empty string argument returns EMPTY singleton")
        void emptyStringReturnsEmpty() {
            assertSame(CharSet.EMPTY, CharSet.getInstance(""),
                "An empty string argument should return the EMPTY singleton");
        }
    }

    @Nested
    @DisplayName("getInstance returns cached CharSet constants for well-known patterns")
    class WhenInputIsAWellKnownPattern {

        @Test
        @DisplayName("\"a-zA-Z\" returns the ASCII_ALPHA singleton")
        void lowerThenUpperAlphaRangeReturnsAsciiAlpha() {
            assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("a-zA-Z"),
                "\"a-zA-Z\" is a registered alias for the ASCII_ALPHA singleton");
        }

        @Test
        @DisplayName("\"A-Za-z\" also returns the ASCII_ALPHA singleton (alternate ordering)")
        void upperThenLowerAlphaRangeReturnsAsciiAlpha() {
            assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("A-Za-z"),
                "\"A-Za-z\" is also a registered alias for the same ASCII_ALPHA singleton");
        }

        @Test
        @DisplayName("\"a-z\" returns the ASCII_ALPHA_LOWER singleton")
        void lowercaseAlphaRangeReturnsAsciiAlphaLower() {
            assertSame(CharSet.ASCII_ALPHA_LOWER, CharSet.getInstance("a-z"),
                "\"a-z\" should return the ASCII_ALPHA_LOWER singleton");
        }

        @Test
        @DisplayName("\"A-Z\" returns the ASCII_ALPHA_UPPER singleton")
        void uppercaseAlphaRangeReturnsAsciiAlphaUpper() {
            assertSame(CharSet.ASCII_ALPHA_UPPER, CharSet.getInstance("A-Z"),
                "\"A-Z\" should return the ASCII_ALPHA_UPPER singleton");
        }

        @Test
        @DisplayName("\"0-9\" returns the ASCII_NUMERIC singleton")
        void digitRangeReturnsAsciiNumeric() {
            assertSame(CharSet.ASCII_NUMERIC, CharSet.getInstance("0-9"),
                "\"0-9\" should return the ASCII_NUMERIC singleton");
        }
    }
}
