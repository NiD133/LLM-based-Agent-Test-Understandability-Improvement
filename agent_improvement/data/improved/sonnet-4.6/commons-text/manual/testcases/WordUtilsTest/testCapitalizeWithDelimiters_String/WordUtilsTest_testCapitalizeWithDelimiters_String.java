package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testCapitalizeWithDelimiters_String {

    // Delimiters used in the main group of tests
    private static final char[] DELIMITERS_DASH_PLUS_SPACE_AT = { '-', '+', ' ', '@' };

    @Test
    void capitalize_nullInput_returnsNull() {
        assertNull(WordUtils.capitalize(null, null));
    }

    @Test
    void capitalize_emptyString_returnsEmpty() {
        assertEquals("", WordUtils.capitalize("", ArrayUtils.EMPTY_CHAR_ARRAY));
    }

    @Test
    void capitalize_whitespaceOnlyWithEmptyDelimiters_returnsUnchanged() {
        assertEquals("  ", WordUtils.capitalize("  ", ArrayUtils.EMPTY_CHAR_ARRAY));
    }

    @Test
    void capitalize_singleCharAlreadyCapitalized_returnsUnchanged() {
        assertEquals("I", WordUtils.capitalize("I", DELIMITERS_DASH_PLUS_SPACE_AT));
    }

    @Test
    void capitalize_singleLowercaseChar_capitalizesIt() {
        assertEquals("I", WordUtils.capitalize("i", DELIMITERS_DASH_PLUS_SPACE_AT));
    }

    @Test
    void capitalize_allLowerCaseWordsWithCustomDelimiters_capitalizesFirstCharAfterEachDelimiter() {
        assertEquals("I-Am Here+123", WordUtils.capitalize("i-am here+123", DELIMITERS_DASH_PLUS_SPACE_AT));
    }

    @Test
    void capitalize_alreadyCapitalizedWords_leavesUpperCaseIntact() {
        assertEquals("I Am+Here-123", WordUtils.capitalize("I Am+Here-123", DELIMITERS_DASH_PLUS_SPACE_AT));
    }

    @Test
    void capitalize_mixedCaseIncludingUpperInMiddle_onlyCapitalizesFirstCharAfterDelimiter() {
        assertEquals("I+Am-HERE 123", WordUtils.capitalize("i+am-HERE 123", DELIMITERS_DASH_PLUS_SPACE_AT));
    }

    @Test
    void capitalize_alreadyFullyCapitalized_remainsUnchanged() {
        assertEquals("I-AM HERE+123", WordUtils.capitalize("I-AM HERE+123", DELIMITERS_DASH_PLUS_SPACE_AT));
    }

    @Test
    void capitalize_dotDelimiter_capitalizesOnlyAfterDot() {
        char[] dotDelimiter = { '.' };
        // Only the character after '.' is capitalized; space is not a delimiter here
        assertEquals("I aM.Fine", WordUtils.capitalize("i aM.fine", dotDelimiter));
    }

    @Test
    void capitalize_nullDelimiters_treatsWhitespaceAsDelimiter() {
        // null delimiter means whitespace splits words; '.' is not a delimiter
        assertEquals("I Am.fine", WordUtils.capitalize("i am.fine", null));
    }
}
