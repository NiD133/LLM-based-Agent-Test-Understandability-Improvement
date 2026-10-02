package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testIsDelimiter {

    @SuppressWarnings("deprecation")
    @Test
    void testIsDelimiter() {
        assertFalse(WordUtils.isDelimiter('.', null));
        assertTrue(WordUtils.isDelimiter(' ', null));

        assertFalse(WordUtils.isDelimiter(' ', new char[] { '.' }));
        assertTrue(WordUtils.isDelimiter('.', new char[] { '.' }));

        assertFalse(WordUtils.isDelimiter(' ', new char[] { '.', '_', 'a' }));
        assertTrue(WordUtils.isDelimiter('.', new char[] { '.', '_', 'a', '.' }));
    }
}
