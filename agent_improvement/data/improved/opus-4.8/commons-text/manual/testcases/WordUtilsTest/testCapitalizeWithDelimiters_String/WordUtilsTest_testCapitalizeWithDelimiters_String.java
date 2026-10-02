package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#capitalize(String, char...)}, the overload that
 * capitalizes the first character of every word, where a "word" boundary is
 * defined by the supplied set of delimiter characters.
 */
public class WordUtilsTest_testCapitalizeWithDelimiters_String {

    /** Delimiters used by most of the assertions below: dash, plus, space and at-sign. */
    private static final char[] DELIMITERS = { '-', '+', ' ', '@' };

    @Test
    void testCapitalizeWithDelimiters_String() {
        // A null input string returns null, regardless of the delimiters.
        assertNull(WordUtils.capitalize(null, null));

        // An empty delimiter array means "no word boundaries", so the input is returned unchanged.
        assertEquals("", WordUtils.capitalize("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.capitalize("  ", ArrayUtils.EMPTY_CHAR_ARRAY));

        // A single character is capitalized; an already-capitalized character is left as-is.
        assertEquals("I", WordUtils.capitalize("I", DELIMITERS));
        assertEquals("I", WordUtils.capitalize("i", DELIMITERS));

        // Each character that follows a delimiter starts a new word and is capitalized.
        // Characters that are already upper case (or are not letters) are not altered.
        assertEquals("I-Am Here+123", WordUtils.capitalize("i-am here+123", DELIMITERS));
        assertEquals("I Am+Here-123", WordUtils.capitalize("I Am+Here-123", DELIMITERS));
        assertEquals("I+Am-HERE 123", WordUtils.capitalize("i+am-HERE 123", DELIMITERS));
        assertEquals("I-AM HERE+123", WordUtils.capitalize("I-AM HERE+123", DELIMITERS));

        // With '.' as the only delimiter, spaces no longer separate words, so only
        // the first character and the character after the dot are capitalized.
        char[] dotDelimiter = { '.' };
        assertEquals("I aM.Fine", WordUtils.capitalize("i aM.fine", dotDelimiter));

        // A null delimiter array falls back to the default behaviour: whitespace separates words.
        assertEquals("I Am.fine", WordUtils.capitalize("i am.fine", null));
    }
}
