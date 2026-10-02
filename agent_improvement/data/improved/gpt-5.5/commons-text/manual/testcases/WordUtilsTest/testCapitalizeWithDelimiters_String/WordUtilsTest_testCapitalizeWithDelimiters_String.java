package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testCapitalizeWithDelimiters_String {

    private static final char[] NO_DELIMITERS = ArrayUtils.EMPTY_CHAR_ARRAY;
    private static final char[] WORD_SEPARATORS = { '-', '+', ' ', '@' };
    private static final char[] PERIOD_SEPARATOR = { '.' };

    @Test
    void testCapitalizeWithDelimiters_String() {
        assertNull(WordUtils.capitalize(null, null));
        assertEquals("", WordUtils.capitalize("", NO_DELIMITERS));
        assertEquals("  ", WordUtils.capitalize("  ", NO_DELIMITERS));

        assertEquals("I", WordUtils.capitalize("I", WORD_SEPARATORS));
        assertEquals("I", WordUtils.capitalize("i", WORD_SEPARATORS));
        assertEquals("I-Am Here+123", WordUtils.capitalize("i-am here+123", WORD_SEPARATORS));
        assertEquals("I Am+Here-123", WordUtils.capitalize("I Am+Here-123", WORD_SEPARATORS));
        assertEquals("I+Am-HERE 123", WordUtils.capitalize("i+am-HERE 123", WORD_SEPARATORS));
        assertEquals("I-AM HERE+123", WordUtils.capitalize("I-AM HERE+123", WORD_SEPARATORS));

        assertEquals("I aM.Fine", WordUtils.capitalize("i aM.fine", PERIOD_SEPARATOR));
        assertEquals("I Am.fine", WordUtils.capitalize("i am.fine", null));
    }
}
