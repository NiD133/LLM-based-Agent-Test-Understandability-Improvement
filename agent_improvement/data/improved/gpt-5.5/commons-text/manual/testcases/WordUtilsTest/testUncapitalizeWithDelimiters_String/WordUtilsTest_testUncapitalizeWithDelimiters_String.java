package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testUncapitalizeWithDelimiters_String {

    @Test
    void testUncapitalizeWithDelimiters_String() {
        assertNull(WordUtils.uncapitalize(null, null));

        assertEquals("", WordUtils.uncapitalize("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.uncapitalize("  ", ArrayUtils.EMPTY_CHAR_ARRAY));

        final char[] wordDelimiters = { '-', '+', ' ', '@' };
        assertEquals("i", WordUtils.uncapitalize("I", wordDelimiters));
        assertEquals("i", WordUtils.uncapitalize("i", wordDelimiters));
        assertEquals("i am-here+123", WordUtils.uncapitalize("i am-here+123", wordDelimiters));
        assertEquals("i+am here-123", WordUtils.uncapitalize("I+Am Here-123", wordDelimiters));
        assertEquals("i-am+hERE 123", WordUtils.uncapitalize("i-am+HERE 123", wordDelimiters));
        assertEquals("i aM-hERE+123", WordUtils.uncapitalize("I AM-HERE+123", wordDelimiters));

        final char[] periodDelimiter = { '.' };
        assertEquals("i AM.fINE", WordUtils.uncapitalize("I AM.FINE", periodDelimiter));

        assertEquals("i aM.FINE", WordUtils.uncapitalize("I AM.FINE", null));
    }
}
