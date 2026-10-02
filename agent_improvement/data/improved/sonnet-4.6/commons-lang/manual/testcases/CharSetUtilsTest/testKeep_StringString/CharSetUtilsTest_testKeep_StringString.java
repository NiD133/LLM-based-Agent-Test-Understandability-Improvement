package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testKeep_StringString extends AbstractLangTest {

    @Test
    void testKeep_nullInputString_returnsNull() {
        assertNull(CharSetUtils.keep(null, (String) null));
        assertNull(CharSetUtils.keep(null, ""));
    }

    @Test
    void testKeep_emptyInputString_returnsEmpty() {
        assertEquals("", CharSetUtils.keep("", (String) null));
        assertEquals("", CharSetUtils.keep("", ""));
        assertEquals("", CharSetUtils.keep("", "a-e"));
    }

    @Test
    void testKeep_nullOrEmptyCharSet_returnsEmpty() {
        assertEquals("", CharSetUtils.keep("hello", (String) null));
        assertEquals("", CharSetUtils.keep("hello", ""));
    }

    @Test
    void testKeep_noCharactersMatch_returnsEmpty() {
        assertEquals("", CharSetUtils.keep("hello", "xyz"));
    }

    @Test
    void testKeep_allCharactersMatch_returnsOriginal() {
        assertEquals("hello", CharSetUtils.keep("hello", "a-z"));
        assertEquals("hello", CharSetUtils.keep("hello", "oleh"));
    }

    @Test
    void testKeep_partialCharactersMatch_returnsMatchingSubset() {
        assertEquals("ell", CharSetUtils.keep("hello", "el"));
    }
}
