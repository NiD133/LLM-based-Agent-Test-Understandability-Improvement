package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testUncapitalizeWithDelimiters_String {

    @Test
    void testUncapitalizeWithDelimiters_String() {
        // null and empty string edge cases
        assertNull(WordUtils.uncapitalize(null, null));
        assertEquals("", WordUtils.uncapitalize("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.uncapitalize("  ", ArrayUtils.EMPTY_CHAR_ARRAY));

        // custom delimiters: dash, plus, space, at-sign
        char[] mixedDelimiters = { '-', '+', ' ', '@' };
        assertEquals("i", WordUtils.uncapitalize("I", mixedDelimiters));
        assertEquals("i", WordUtils.uncapitalize("i", mixedDelimiters));
        assertEquals("i am-here+123", WordUtils.uncapitalize("i am-here+123", mixedDelimiters));
        assertEquals("i+am here-123", WordUtils.uncapitalize("I+Am Here-123", mixedDelimiters));
        assertEquals("i-am+hERE 123", WordUtils.uncapitalize("i-am+HERE 123", mixedDelimiters));
        assertEquals("i aM-hERE+123", WordUtils.uncapitalize("I AM-HERE+123", mixedDelimiters));

        // custom delimiter: dot only
        char[] dotDelimiter = { '.' };
        assertEquals("i AM.fINE", WordUtils.uncapitalize("I AM.FINE", dotDelimiter));

        // null delimiter falls back to whitespace as word separator
        assertEquals("i aM.FINE", WordUtils.uncapitalize("I AM.FINE", null));
    }
}
