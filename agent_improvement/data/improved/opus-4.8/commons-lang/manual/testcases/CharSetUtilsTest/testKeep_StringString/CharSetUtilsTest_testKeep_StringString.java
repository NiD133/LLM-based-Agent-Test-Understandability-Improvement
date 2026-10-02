package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSetUtils#keep(String, String...)}.
 *
 * <p>{@code keep} returns a copy of the input string that retains only the
 * characters that belong to the supplied set. The cases below are organised by
 * the input that drives the result: a {@code null} string, an empty string,
 * and a non-empty string combined with various sets.</p>
 */
public class CharSetUtilsTest_testKeep_StringString extends AbstractLangTest {

    @Test
    void testKeep_StringString() {
        // A null input string always yields null, regardless of the set.
        assertNull(CharSetUtils.keep(null, (String) null));
        assertNull(CharSetUtils.keep(null, ""));

        // An empty input string always yields an empty string.
        assertEquals("", CharSetUtils.keep("", (String) null));
        assertEquals("", CharSetUtils.keep("", ""));
        assertEquals("", CharSetUtils.keep("", "a-e"));

        // A null or empty set keeps nothing, so the result is empty.
        assertEquals("", CharSetUtils.keep("hello", (String) null));
        assertEquals("", CharSetUtils.keep("hello", ""));

        // A set sharing no characters with the input also yields an empty string.
        assertEquals("", CharSetUtils.keep("hello", "xyz"));

        // A set covering every character keeps the whole string.
        assertEquals("hello", CharSetUtils.keep("hello", "a-z"));
        assertEquals("hello", CharSetUtils.keep("hello", "oleh"));

        // A partial set keeps only the matching characters, preserving their order.
        assertEquals("ell", CharSetUtils.keep("hello", "el"));
    }
}
