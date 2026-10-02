package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testContainsAllWordsWithNull {

    @Test
    void testContainsAllWordsWithNull() {
        assertFalse(WordUtils.containsAllWords("M", (CharSequence) null));
    }
}
