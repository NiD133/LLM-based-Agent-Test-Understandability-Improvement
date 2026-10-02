package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testCapitalizeFullyWithDelimiters_String {

    @Test
    void testCapitalizeFullyWithDelimiters_String() {
        // null and empty string edge cases
        assertNull(WordUtils.capitalizeFully(null, null));
        assertEquals("", WordUtils.capitalizeFully("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.capitalizeFully("  ", ArrayUtils.EMPTY_CHAR_ARRAY));

        // custom delimiters: hyphen, plus, space, at-sign — each triggers capitalization of the next char
        char[] multiDelimiters = { '-', '+', ' ', '@' };
        assertEquals("I", WordUtils.capitalizeFully("I", multiDelimiters));
        assertEquals("I", WordUtils.capitalizeFully("i", multiDelimiters));
        assertEquals("I-Am Here+123", WordUtils.capitalizeFully("i-am here+123", multiDelimiters));
        assertEquals("I Am+Here-123", WordUtils.capitalizeFully("I Am+Here-123", multiDelimiters));
        assertEquals("I+Am-Here 123", WordUtils.capitalizeFully("i+am-HERE 123", multiDelimiters));
        assertEquals("I-Am Here+123", WordUtils.capitalizeFully("I-AM HERE+123", multiDelimiters));

        // dot-only delimiter: only '.' triggers capitalization, spaces do not
        char[] dotDelimiter = { '.' };
        assertEquals("I am.Fine", WordUtils.capitalizeFully("i aM.fine", dotDelimiter));

        // null delimiter falls back to whitespace splitting
        assertEquals("I Am.fine", WordUtils.capitalizeFully("i am.fine", null));

        // single word with whitespace delimiter capitalizes just the first letter
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", null));

        // delimiter that never appears in the string: still capitalizes the first character
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", new char[] { '!' }));
    }
}
