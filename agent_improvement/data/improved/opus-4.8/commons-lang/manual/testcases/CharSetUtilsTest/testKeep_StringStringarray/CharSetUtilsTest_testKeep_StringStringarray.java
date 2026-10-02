package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSetUtils#keep(String, String...)}, which returns a copy of the
 * input string containing only the characters that belong to the given set.
 */
public class CharSetUtilsTest_testKeep_StringStringarray extends AbstractLangTest {

    @Test
    void testKeep_StringStringarray() {
        // A null input string always yields null, regardless of the set.
        assertNull(CharSetUtils.keep(null, (String[]) null));
        assertNull(CharSetUtils.keep(null));
        assertNull(CharSetUtils.keep(null, (String) null));
        assertNull(CharSetUtils.keep(null, "a-e"));

        // An empty input string always yields an empty string, regardless of the set.
        assertEquals("", CharSetUtils.keep("", (String[]) null));
        assertEquals("", CharSetUtils.keep(""));
        assertEquals("", CharSetUtils.keep("", (String) null));
        assertEquals("", CharSetUtils.keep("", "a-e"));

        // A null or absent set means "keep nothing", so the result is empty.
        assertEquals("", CharSetUtils.keep("hello", (String[]) null));
        assertEquals("", CharSetUtils.keep("hello"));
        assertEquals("", CharSetUtils.keep("hello", (String) null));

        // Only characters that are members of the set are kept, in their original order.
        assertEquals("e", CharSetUtils.keep("hello", "a-e"));
        assertEquals("ell", CharSetUtils.keep("hello", "el"));
        assertEquals("ll", CharSetUtils.keep("hello", "l"));

        // A set covering every character keeps the whole string.
        assertEquals("hello", CharSetUtils.keep("hello", "elho"));
        assertEquals("hello", CharSetUtils.keep("hello", "a-z"));

        // Repeated characters are each kept when the character is in the set.
        assertEquals("----", CharSetUtils.keep("----", "-"));
    }
}
