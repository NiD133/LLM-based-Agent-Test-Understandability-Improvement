package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testKeep_StringStringarray extends AbstractLangTest {

    // keep(null, ...) returns null regardless of the set argument
    @Test
    void testKeep_nullString_returnsNull() {
        assertNull(CharSetUtils.keep(null, (String[]) null));
        assertNull(CharSetUtils.keep(null));
        assertNull(CharSetUtils.keep(null, (String) null));
        assertNull(CharSetUtils.keep(null, "a-e"));
    }

    // keep("", ...) returns "" regardless of the set argument
    @Test
    void testKeep_emptyString_returnsEmpty() {
        assertEquals("", CharSetUtils.keep("", (String[]) null));
        assertEquals("", CharSetUtils.keep(""));
        assertEquals("", CharSetUtils.keep("", (String) null));
        assertEquals("", CharSetUtils.keep("", "a-e"));
    }

    // keep(nonEmpty, null/empty set) returns "" because no chars match an empty set
    @Test
    void testKeep_nonEmptyString_nullOrEmptySet_returnsEmpty() {
        assertEquals("", CharSetUtils.keep("hello", (String[]) null));
        assertEquals("", CharSetUtils.keep("hello"));
        assertEquals("", CharSetUtils.keep("hello", (String) null));
    }

    // keep retains only characters that fall within the specified set
    @Test
    void testKeep_nonEmptyString_withSet_returnsMatchingChars() {
        assertEquals("e",     CharSetUtils.keep("hello", "a-e"));   // range: only 'e' in "hello" is in a-e
        assertEquals("e",     CharSetUtils.keep("hello", "a-e"));   // same call repeated in original
        assertEquals("ell",   CharSetUtils.keep("hello", "el"));    // explicit chars: keep 'e' and 'l'
        assertEquals("hello", CharSetUtils.keep("hello", "elho"));  // explicit chars: keep all letters in "hello"
        assertEquals("hello", CharSetUtils.keep("hello", "a-z"));   // range: all lowercase letters kept
        assertEquals("----",  CharSetUtils.keep("----", "-"));      // literal hyphen retained
        assertEquals("ll",    CharSetUtils.keep("hello", "l"));     // only 'l' chars kept
    }
}
