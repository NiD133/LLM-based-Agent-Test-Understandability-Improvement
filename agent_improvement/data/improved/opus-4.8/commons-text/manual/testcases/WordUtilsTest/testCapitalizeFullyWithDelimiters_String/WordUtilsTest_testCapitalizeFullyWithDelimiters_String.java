package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#capitalizeFully(String, char...)}, the variant that
 * lets the caller choose which characters separate words.
 *
 * <p>"Capitalize fully" means each word is title-cased: its first character is
 * upper-cased and every remaining character is lower-cased. A new word begins
 * after any of the supplied delimiter characters.</p>
 */
public class WordUtilsTest_testCapitalizeFullyWithDelimiters_String {

    /** Delimiters used by the multi-delimiter cases: dash, plus, space and at-sign. */
    private static final char[] DASH_PLUS_SPACE_AT = {'-', '+', ' ', '@'};

    /** Delimiter used by the single-delimiter cases: a dot. */
    private static final char[] DOT = {'.'};

    @Test
    void testCapitalizeFullyWithDelimiters_String() {
        // A null input string is returned unchanged (as null).
        assertNull(WordUtils.capitalizeFully(null, null));

        // With an empty delimiter array there are no word boundaries, so the
        // input is returned unchanged.
        assertEquals("", WordUtils.capitalizeFully("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.capitalizeFully("  ", ArrayUtils.EMPTY_CHAR_ARRAY));

        // Single-letter words are title-cased regardless of original case.
        assertEquals("I", WordUtils.capitalizeFully("I", DASH_PLUS_SPACE_AT));
        assertEquals("I", WordUtils.capitalizeFully("i", DASH_PLUS_SPACE_AT));

        // Each delimiter ('-', '+', ' ') starts a new word; digits are left as-is.
        assertEquals("I-Am Here+123", WordUtils.capitalizeFully("i-am here+123", DASH_PLUS_SPACE_AT));
        assertEquals("I Am+Here-123", WordUtils.capitalizeFully("I Am+Here-123", DASH_PLUS_SPACE_AT));
        assertEquals("I+Am-Here 123", WordUtils.capitalizeFully("i+am-HERE 123", DASH_PLUS_SPACE_AT));
        assertEquals("I-Am Here+123", WordUtils.capitalizeFully("I-AM HERE+123", DASH_PLUS_SPACE_AT));

        // With only '.' as a delimiter, the space no longer separates words, so
        // "aM" becomes part of the first word "I am".
        assertEquals("I am.Fine", WordUtils.capitalizeFully("i aM.fine", DOT));

        // A null delimiter array falls back to the default behaviour, where
        // whitespace separates words.
        assertEquals("I Am.fine", WordUtils.capitalizeFully("i am.fine", null));

        // A single word with default (whitespace) delimiters.
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", null));

        // When no supplied delimiter ('!') appears in the input, the whole
        // string is treated as one word.
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", new char[] {'!'}));
    }
}
