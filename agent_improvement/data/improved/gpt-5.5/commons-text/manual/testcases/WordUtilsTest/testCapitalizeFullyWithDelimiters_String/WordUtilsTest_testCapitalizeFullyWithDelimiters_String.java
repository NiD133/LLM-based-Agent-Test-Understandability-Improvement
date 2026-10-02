package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testCapitalizeFullyWithDelimiters_String {

    @Test
    void testCapitalizeFullyWithDelimiters_String() {
        assertNull(WordUtils.capitalizeFully(null, null));
        assertEquals("", WordUtils.capitalizeFully("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.capitalizeFully("  ", ArrayUtils.EMPTY_CHAR_ARRAY));

        final char[] hyphenPlusSpaceAtDelimiters = { '-', '+', ' ', '@' };
        assertEquals("I", WordUtils.capitalizeFully("I", hyphenPlusSpaceAtDelimiters));
        assertEquals("I", WordUtils.capitalizeFully("i", hyphenPlusSpaceAtDelimiters));
        assertEquals("I-Am Here+123", WordUtils.capitalizeFully("i-am here+123", hyphenPlusSpaceAtDelimiters));
        assertEquals("I Am+Here-123", WordUtils.capitalizeFully("I Am+Here-123", hyphenPlusSpaceAtDelimiters));
        assertEquals("I+Am-Here 123", WordUtils.capitalizeFully("i+am-HERE 123", hyphenPlusSpaceAtDelimiters));
        assertEquals("I-Am Here+123", WordUtils.capitalizeFully("I-AM HERE+123", hyphenPlusSpaceAtDelimiters));

        final char[] periodDelimiter = { '.' };
        assertEquals("I am.Fine", WordUtils.capitalizeFully("i aM.fine", periodDelimiter));
        assertEquals("I Am.fine", WordUtils.capitalizeFully("i am.fine", null));

        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", null));
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", new char[] { '!' }));
    }
}
