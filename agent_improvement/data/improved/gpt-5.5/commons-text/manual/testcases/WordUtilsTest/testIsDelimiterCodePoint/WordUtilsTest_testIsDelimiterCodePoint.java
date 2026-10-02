package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testIsDelimiterCodePoint {

    @Test
    void testIsDelimiterCodePoint() {
        assertFalse(WordUtils.isDelimiter((int) '.', null), "A period is not a default whitespace delimiter");
        assertTrue(WordUtils.isDelimiter((int) ' ', null), "A space is a default whitespace delimiter");

        assertFalse(WordUtils.isDelimiter((int) ' ', new char[] { '.' }), "A space is not a custom period delimiter");
        assertTrue(WordUtils.isDelimiter((int) '.', new char[] { '.' }), "A period matches the custom period delimiter");

        assertFalse(WordUtils.isDelimiter((int) ' ', new char[] { '.', '_', 'a' }), "A space is absent from the custom delimiter set");
        assertTrue(WordUtils.isDelimiter((int) '.', new char[] { '.', '_', 'a', '.' }), "A period matches even when the delimiter set contains duplicates");
    }
}
