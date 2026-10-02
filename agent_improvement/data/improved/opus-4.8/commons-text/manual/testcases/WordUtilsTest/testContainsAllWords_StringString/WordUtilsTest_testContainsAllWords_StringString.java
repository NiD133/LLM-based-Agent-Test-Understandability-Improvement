package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#containsAllWords(CharSequence, CharSequence...)}.
 *
 * <p>The method returns {@code true} only when the text is non-empty and every
 * search word is a non-blank, whole-word match within the text.</p>
 */
public class WordUtilsTest_testContainsAllWords_StringString {

    @Test
    void testContainsAllWords_StringString() {
        // A null or empty text can never contain any words.
        assertFalse(WordUtils.containsAllWords(null));
        assertFalse(WordUtils.containsAllWords(null, ""));
        assertFalse(WordUtils.containsAllWords(null, "ab"));
        assertFalse(WordUtils.containsAllWords(""));
        assertFalse(WordUtils.containsAllWords("", (String) null));
        assertFalse(WordUtils.containsAllWords("", ""));
        assertFalse(WordUtils.containsAllWords("", "ab"));

        // With no search words (or only null/empty ones), there is nothing to match.
        assertFalse(WordUtils.containsAllWords("foo"));
        assertFalse(WordUtils.containsAllWords("foo", (String) null));
        assertFalse(WordUtils.containsAllWords("bar", ""));

        // Matches must be on whole-word boundaries, not arbitrary substrings.
        assertFalse(WordUtils.containsAllWords("zzabyycdxx", "by"));

        // All words present as whole words -> true.
        assertTrue(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", "lorem", "dolor"));

        // A single null among the search words makes the whole check fail.
        assertFalse(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", null, "lorem", "dolor"));
        assertFalse(WordUtils.containsAllWords("lorem ipsum null dolor sit amet", "ipsum", null, "lorem", "dolor"));

        // Single-character search words that are not whole words in the text.
        assertFalse(WordUtils.containsAllWords("ab", "b"));
        assertFalse(WordUtils.containsAllWords("ab", "z"));

        // Regex metacharacters are treated literally, so they are not found in "ab".
        assertFalse(WordUtils.containsAllWords("ab", "["));
        assertFalse(WordUtils.containsAllWords("ab", "]"));
        assertFalse(WordUtils.containsAllWords("ab", "*"));

        // Both words appear as whole words separated by whitespace -> true.
        assertTrue(WordUtils.containsAllWords("ab x", "ab", "x"));
    }
}
