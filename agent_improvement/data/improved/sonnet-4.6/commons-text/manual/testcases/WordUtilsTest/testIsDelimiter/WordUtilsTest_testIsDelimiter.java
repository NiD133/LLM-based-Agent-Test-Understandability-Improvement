package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testIsDelimiter {

    @SuppressWarnings("deprecation")
    @Test
    void testIsDelimiter_nullDelimiters_fallsBackToWhitespace() {
        // null delimiters → Character.isWhitespace() is used
        assertFalse(WordUtils.isDelimiter('.', null)); // '.' is not whitespace
        assertTrue(WordUtils.isDelimiter(' ', null));  // ' ' is whitespace
    }

    @SuppressWarnings("deprecation")
    @Test
    void testIsDelimiter_explicitDelimiters_matchesOnlyListedChars() {
        // space is not a delimiter when the set contains only '.'
        assertFalse(WordUtils.isDelimiter(' ', new char[] { '.' }));
        // '.' is a delimiter when it is explicitly listed
        assertTrue(WordUtils.isDelimiter('.', new char[] { '.' }));
        // space is not in the multi-char delimiter set {'.', '_', 'a'}
        assertFalse(WordUtils.isDelimiter(' ', new char[] { '.', '_', 'a' }));
        // '.' matches in a multi-char set that contains it (duplicate entries allowed)
        assertTrue(WordUtils.isDelimiter('.', new char[] { '.', '_', 'a', '.' }));
    }
}
