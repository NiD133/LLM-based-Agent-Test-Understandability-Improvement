package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSetUtils#squeeze(String, String...)}, which collapses runs of
 * repeated characters down to a single character when that character is part of
 * the supplied set.
 */
public class CharSetUtilsTest_testSqueeze_StringStringarray extends AbstractLangTest {

    @Test
    void testSqueeze_StringStringarray() {
        // A null input string always yields null, regardless of the set.
        assertNull(CharSetUtils.squeeze(null, (String[]) null));
        assertNull(CharSetUtils.squeeze(null));
        assertNull(CharSetUtils.squeeze(null, (String) null));
        assertNull(CharSetUtils.squeeze(null, "el"));

        // An empty input string always yields an empty string.
        assertEquals("", CharSetUtils.squeeze("", (String[]) null));
        assertEquals("", CharSetUtils.squeeze(""));
        assertEquals("", CharSetUtils.squeeze("", (String) null));
        assertEquals("", CharSetUtils.squeeze("", "a-e"));

        // A null, empty, or non-matching set leaves the input string unchanged.
        assertEquals("hello", CharSetUtils.squeeze("hello", (String[]) null));
        assertEquals("hello", CharSetUtils.squeeze("hello"));
        assertEquals("hello", CharSetUtils.squeeze("hello", (String) null));
        assertEquals("hello", CharSetUtils.squeeze("hello", "a-e"));

        // Only repeated characters that are in the set get squeezed.
        // "hello" -> "helo": the "ll" run collapses because both 'e' and 'l' are in "el".
        assertEquals("helo", CharSetUtils.squeeze("hello", "el"));
        // "hello" stays "hello": 'l' is repeated but is not in the set "e".
        assertEquals("hello", CharSetUtils.squeeze("hello", "e"));

        // Multiple runs of set characters each collapse to a single character.
        // "fooffooff" -> "fofof": every "oo" and "ff" run is squeezed (both in "of").
        assertEquals("fofof", CharSetUtils.squeeze("fooffooff", "of"));
        // "fooooff" -> "fof": the "oooo" and "ff" runs collapse (both in "fo").
        assertEquals("fof", CharSetUtils.squeeze("fooooff", "fo"));
    }
}
