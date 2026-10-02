package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testContainsAllWordsWithNull {

    @Test
    @DisplayName("containsAllWords returns false when the search word argument is null")
    void testContainsAllWordsWithNull() {
        assertFalse(WordUtils.containsAllWords("M", (CharSequence) null));
    }
}
