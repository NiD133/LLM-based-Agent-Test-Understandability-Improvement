package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testContainsAllWordsWithNull {

    @Test
    void testContainsAllWordsWithNull() {
        // When the words-to-search array is null, no word can be found,
        // so containsAllWords must return false regardless of the input text.
        assertFalse(WordUtils.containsAllWords("M", (CharSequence) null));
    }
}
