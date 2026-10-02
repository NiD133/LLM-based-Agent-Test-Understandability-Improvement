package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testIsDelimiterCodePoint {

    private static final int DOT_CODE_POINT   = (int) '.';
    private static final int SPACE_CODE_POINT = (int) ' ';

    // --- null delimiters: whitespace characters are delimiters ---

    @Test
    void testIsDelimiter_nullDelimiters_nonWhitespace_returnsFalse() {
        assertFalse(WordUtils.isDelimiter(DOT_CODE_POINT, null));
    }

    @Test
    void testIsDelimiter_nullDelimiters_whitespace_returnsTrue() {
        assertTrue(WordUtils.isDelimiter(SPACE_CODE_POINT, null));
    }

    // --- custom delimiter array: only listed characters are delimiters ---

    @Test
    void testIsDelimiter_customDelimiters_charNotInSet_returnsFalse() {
        assertFalse(WordUtils.isDelimiter(SPACE_CODE_POINT, new char[] { '.' }));
    }

    @Test
    void testIsDelimiter_customDelimiters_charInSet_returnsTrue() {
        assertTrue(WordUtils.isDelimiter(DOT_CODE_POINT, new char[] { '.' }));
    }

    @Test
    void testIsDelimiter_multipleCustomDelimiters_charNotInSet_returnsFalse() {
        assertFalse(WordUtils.isDelimiter(SPACE_CODE_POINT, new char[] { '.', '_', 'a' }));
    }

    @Test
    void testIsDelimiter_multipleCustomDelimiters_charInSetMultipleTimes_returnsTrue() {
        // '.' appears twice in the delimiter array; the method should still return true
        assertTrue(WordUtils.isDelimiter(DOT_CODE_POINT, new char[] { '.', '_', 'a', '.' }));
    }
}
