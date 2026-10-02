package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testContainsAllWords_StringString {

    @Test
    void containsAllWords_returnsFalse_whenSentenceIsNull() {
        assertFalse(WordUtils.containsAllWords(null));
        assertFalse(WordUtils.containsAllWords(null, ""));
        assertFalse(WordUtils.containsAllWords(null, "ab"));
    }

    @Test
    void containsAllWords_returnsFalse_whenSentenceIsEmpty() {
        assertFalse(WordUtils.containsAllWords(""));
        assertFalse(WordUtils.containsAllWords("", (String) null));
        assertFalse(WordUtils.containsAllWords("", ""));
        assertFalse(WordUtils.containsAllWords("", "ab"));
    }

    @Test
    void containsAllWords_returnsFalse_whenSearchWordsArrayIsNullOrEmpty() {
        assertFalse(WordUtils.containsAllWords("foo"));
        assertFalse(WordUtils.containsAllWords("foo", (String) null));
        assertFalse(WordUtils.containsAllWords("bar", ""));
    }

    @Test
    void containsAllWords_returnsFalse_whenSearchWordIsOnlySubstring() {
        // "by" appears inside "zzabyycdxx" but is not a whole word
        assertFalse(WordUtils.containsAllWords("zzabyycdxx", "by"));
        // "b" is not a whole word in "ab"
        assertFalse(WordUtils.containsAllWords("ab", "b"));
        // "z" does not appear at all
        assertFalse(WordUtils.containsAllWords("ab", "z"));
    }

    @Test
    void containsAllWords_returnsFalse_whenSearchWordIsSpecialRegexCharacter() {
        // Special regex chars are treated as literals and will not match as whole words in "ab"
        assertFalse(WordUtils.containsAllWords("ab", "["));
        assertFalse(WordUtils.containsAllWords("ab", "]"));
        assertFalse(WordUtils.containsAllWords("ab", "*"));
    }

    @Test
    void containsAllWords_returnsFalse_whenAnySearchWordInArrayIsNull() {
        // null in the words array makes the whole call return false
        assertFalse(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", null, "lorem", "dolor"));
        // Even if the literal text "null" appears in the sentence, a null element still returns false
        assertFalse(WordUtils.containsAllWords("lorem ipsum null dolor sit amet", "ipsum", null, "lorem", "dolor"));
    }

    @Test
    void containsAllWords_returnsTrue_whenAllSearchWordsAreWholeWordsInSentence() {
        assertTrue(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", "lorem", "dolor"));
        assertTrue(WordUtils.containsAllWords("ab x", "ab", "x"));
    }
}
