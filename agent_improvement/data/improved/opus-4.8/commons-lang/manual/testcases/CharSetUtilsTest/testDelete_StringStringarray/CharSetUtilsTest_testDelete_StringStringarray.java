package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSetUtils#delete(String, String...)}.
 *
 * <p>{@code delete} removes from the first argument every character that
 * belongs to the set described by the remaining arguments (see
 * {@link CharSet} for the set-syntax, e.g. {@code "a-e"} is a range).</p>
 */
public class CharSetUtilsTest_testDelete_StringStringarray extends AbstractLangTest {

    @Test
    void testDelete_StringStringarray() {
        // A null input string always yields null, regardless of the set.
        assertNull(CharSetUtils.delete(null, (String[]) null));
        assertNull(CharSetUtils.delete(null));
        assertNull(CharSetUtils.delete(null, (String) null));
        assertNull(CharSetUtils.delete(null, "el"));

        // An empty input string always yields an empty string, regardless of the set.
        assertEquals("", CharSetUtils.delete("", (String[]) null));
        assertEquals("", CharSetUtils.delete(""));
        assertEquals("", CharSetUtils.delete("", (String) null));
        assertEquals("", CharSetUtils.delete("", "a-e"));

        // A null or empty set leaves the input string unchanged (nothing to delete).
        assertEquals("hello", CharSetUtils.delete("hello", (String[]) null));
        assertEquals("hello", CharSetUtils.delete("hello"));
        assertEquals("hello", CharSetUtils.delete("hello", (String) null));
        assertEquals("hello", CharSetUtils.delete("hello", ""));

        // A set containing none of the input characters also leaves it unchanged.
        assertEquals("hello", CharSetUtils.delete("hello", "xyz"));

        // Characters listed in the set are removed from the input string.
        assertEquals("ho", CharSetUtils.delete("hello", "el"));
        assertEquals("heo", CharSetUtils.delete("hello", "l"));

        // When the set covers every input character, the result is empty.
        assertEquals("", CharSetUtils.delete("hello", "elho"));
        assertEquals("", CharSetUtils.delete("hello", "a-z"));
        assertEquals("", CharSetUtils.delete("----", "-"));
    }
}
