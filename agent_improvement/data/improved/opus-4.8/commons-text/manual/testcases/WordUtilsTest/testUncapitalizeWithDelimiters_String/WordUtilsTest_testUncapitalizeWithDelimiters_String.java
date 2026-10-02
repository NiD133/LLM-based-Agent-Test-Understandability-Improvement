package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#uncapitalize(String, char...)}.
 *
 * <p>This method lower-cases the first character of each word, where a "word" is the run of
 * characters that follows one of the supplied delimiters (or the start of the string). All other
 * characters are left untouched.</p>
 */
public class WordUtilsTest_testUncapitalizeWithDelimiters_String {

    /** Delimiters used by most of the cases below: dash, plus, space and at-sign. */
    private static final char[] DELIMITERS = { '-', '+', ' ', '@' };

    @Test
    void returnsNullWhenInputIsNull() {
        assertNull(WordUtils.uncapitalize(null, null));
    }

    @Test
    void leavesEmptyAndBlankStringsUnchanged() {
        assertEquals("", WordUtils.uncapitalize("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.uncapitalize("  ", ArrayUtils.EMPTY_CHAR_ARRAY));
    }

    @Test
    void uncapitalizesFirstCharacterOfSingleCharacterWord() {
        // Only the leading character is affected; an already-lowercase input stays the same.
        assertEquals("i", WordUtils.uncapitalize("I", DELIMITERS));
        assertEquals("i", WordUtils.uncapitalize("i", DELIMITERS));
    }

    @Test
    void uncapitalizesFirstCharacterAfterEachDelimiter() {
        // Already lowercase first characters stay unchanged.
        assertEquals("i am-here+123", WordUtils.uncapitalize("i am-here+123", DELIMITERS));
        // The first character of each delimited word is lower-cased.
        assertEquals("i+am here-123", WordUtils.uncapitalize("I+Am Here-123", DELIMITERS));
        // Non-leading characters keep their case, even when upper-case.
        assertEquals("i-am+hERE 123", WordUtils.uncapitalize("i-am+HERE 123", DELIMITERS));
        assertEquals("i aM-hERE+123", WordUtils.uncapitalize("I AM-HERE+123", DELIMITERS));
    }

    @Test
    void usesOnlyTheSuppliedDelimitersToSplitWords() {
        // With '.' as the sole delimiter, the space no longer starts a new word,
        // so "AM" is left untouched while the words after the start and after '.' are lowered.
        char[] dotDelimiter = { '.' };
        assertEquals("i AM.fINE", WordUtils.uncapitalize("I AM.FINE", dotDelimiter));
    }

    @Test
    void treatsNullDelimitersAsWhitespace() {
        // A null delimiter array falls back to whitespace, so the space starts a new word
        // ("FINE" stays untouched because it follows '.', which is not a delimiter here).
        assertEquals("i aM.FINE", WordUtils.uncapitalize("I AM.FINE", null));
    }
}
