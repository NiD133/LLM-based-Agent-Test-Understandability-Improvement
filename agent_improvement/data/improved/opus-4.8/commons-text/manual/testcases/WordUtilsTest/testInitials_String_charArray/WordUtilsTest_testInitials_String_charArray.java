package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#initials(String, char...)}.
 *
 * <p>Each test method exercises one delimiter configuration. The delimiter set decides
 * where words begin: the first character after every delimiter (and the very first
 * character of the string) contributes one character to the result.</p>
 */
public class WordUtilsTest_testInitials_String_charArray {

    // Sample inputs, reused across the delimiter configurations below.
    private static final String EMPTY = "";
    private static final String BLANK = "  ";
    private static final String SINGLE_UPPER = "I";
    private static final String SINGLE_LOWER = "i";
    private static final String AIRPORT_CODE = "SJC";
    private static final String FULL_NAME = "Ben John Lee";
    private static final String FULL_NAME_MIXED_WHITESPACE = "   Ben \n   John\tLee\t";
    private static final String NAME_WITH_DOT = "Ben J.Lee";
    private static final String NAME_WITH_SPACED_DOT = " Ben   John  . Lee";
    private static final String NAME_WITH_APOSTROPHE = "Kay O'Murphy";
    private static final String SENTENCE_WITH_DIGITS = "i am here 123";

    /** Asserts the initials extracted from {@code input} using {@code delimiters}. */
    private static void assertInitials(final String expected, final String input, final char[] delimiters) {
        assertEquals(expected, WordUtils.initials(input, delimiters));
    }

    @Test
    void nullDelimiters_treatsWhitespaceAsWordSeparator() {
        final char[] delimiters = null;

        assertNull(WordUtils.initials(null, delimiters));
        assertInitials("", EMPTY, delimiters);
        assertInitials("", BLANK, delimiters);
        assertInitials("I", SINGLE_UPPER, delimiters);
        assertInitials("i", SINGLE_LOWER, delimiters);
        assertInitials("S", AIRPORT_CODE, delimiters);
        assertInitials("BJL", FULL_NAME, delimiters);
        assertInitials("BJL", FULL_NAME_MIXED_WHITESPACE, delimiters);
        assertInitials("BJ", NAME_WITH_DOT, delimiters);
        assertInitials("BJ.L", NAME_WITH_SPACED_DOT, delimiters);
        assertInitials("KO", NAME_WITH_APOSTROPHE, delimiters);
        assertInitials("iah1", SENTENCE_WITH_DIGITS, delimiters);
    }

    @Test
    void emptyDelimiterArray_alwaysYieldsEmptyString() {
        final char[] delimiters = ArrayUtils.EMPTY_CHAR_ARRAY;

        assertNull(WordUtils.initials(null, delimiters));
        assertInitials("", EMPTY, delimiters);
        assertInitials("", BLANK, delimiters);
        assertInitials("", SINGLE_UPPER, delimiters);
        assertInitials("", SINGLE_LOWER, delimiters);
        assertInitials("", AIRPORT_CODE, delimiters);
        assertInitials("", FULL_NAME, delimiters);
        assertInitials("", FULL_NAME_MIXED_WHITESPACE, delimiters);
        assertInitials("", NAME_WITH_DOT, delimiters);
        assertInitials("", NAME_WITH_SPACED_DOT, delimiters);
        assertInitials("", NAME_WITH_APOSTROPHE, delimiters);
        assertInitials("", SENTENCE_WITH_DIGITS, delimiters);
    }

    @Test
    void spaceDelimiter_splitsOnSpacesOnly() {
        final char[] delimiters = " ".toCharArray();

        assertNull(WordUtils.initials(null, delimiters));
        assertInitials("", EMPTY, delimiters);
        assertInitials("", BLANK, delimiters);
        assertInitials("I", SINGLE_UPPER, delimiters);
        assertInitials("i", SINGLE_LOWER, delimiters);
        assertInitials("S", AIRPORT_CODE, delimiters);
        assertInitials("BJL", FULL_NAME, delimiters);
        assertInitials("BJ", NAME_WITH_DOT, delimiters);
        // Only the space is a delimiter, so tabs and newlines stay part of the words.
        assertInitials("B\nJ", FULL_NAME_MIXED_WHITESPACE, delimiters);
        assertInitials("BJ.L", NAME_WITH_SPACED_DOT, delimiters);
        assertInitials("KO", NAME_WITH_APOSTROPHE, delimiters);
        assertInitials("iah1", SENTENCE_WITH_DIGITS, delimiters);
    }

    @Test
    void spaceAndDotDelimiters_splitOnEither() {
        final char[] delimiters = " .".toCharArray();

        assertNull(WordUtils.initials(null, delimiters));
        assertInitials("", EMPTY, delimiters);
        assertInitials("", BLANK, delimiters);
        assertInitials("I", SINGLE_UPPER, delimiters);
        assertInitials("i", SINGLE_LOWER, delimiters);
        assertInitials("S", AIRPORT_CODE, delimiters);
        assertInitials("BJL", FULL_NAME, delimiters);
        // The dot now starts a new word, so the letter after it is included.
        assertInitials("BJL", NAME_WITH_DOT, delimiters);
        assertInitials("BJL", NAME_WITH_SPACED_DOT, delimiters);
        assertInitials("KO", NAME_WITH_APOSTROPHE, delimiters);
        assertInitials("iah1", SENTENCE_WITH_DIGITS, delimiters);
    }

    @Test
    void spaceDotAndApostropheDelimiters_splitOnAnyOfThem() {
        final char[] delimiters = " .'".toCharArray();

        assertNull(WordUtils.initials(null, delimiters));
        assertInitials("", EMPTY, delimiters);
        assertInitials("", BLANK, delimiters);
        assertInitials("I", SINGLE_UPPER, delimiters);
        assertInitials("i", SINGLE_LOWER, delimiters);
        assertInitials("S", AIRPORT_CODE, delimiters);
        assertInitials("BJL", FULL_NAME, delimiters);
        assertInitials("BJL", NAME_WITH_DOT, delimiters);
        assertInitials("BJL", NAME_WITH_SPACED_DOT, delimiters);
        // The apostrophe is a delimiter too, so "Murphy" contributes its 'M'.
        assertInitials("KOM", NAME_WITH_APOSTROPHE, delimiters);
        assertInitials("iah1", SENTENCE_WITH_DIGITS, delimiters);
    }

    @Test
    void letterAndDigitDelimiters_splitOnTheGivenCharacters() {
        // Here the delimiters are ordinary letters/digits, not whitespace, so the
        // character kept is whatever follows one of S, I, J, o or 1.
        final char[] delimiters = "SIJo1".toCharArray();

        assertNull(WordUtils.initials(null, delimiters));
        assertInitials("", EMPTY, delimiters);
        assertInitials(" ", BLANK, delimiters);
        assertInitials("", SINGLE_UPPER, delimiters);
        assertInitials("i", SINGLE_LOWER, delimiters);
        assertInitials("C", AIRPORT_CODE, delimiters);
        assertInitials("Bh", FULL_NAME, delimiters);
        assertInitials("B.", NAME_WITH_DOT, delimiters);
        assertInitials(" h", NAME_WITH_SPACED_DOT, delimiters);
        assertInitials("K", NAME_WITH_APOSTROPHE, delimiters);
        assertInitials("i2", SENTENCE_WITH_DIGITS, delimiters);
    }
}
