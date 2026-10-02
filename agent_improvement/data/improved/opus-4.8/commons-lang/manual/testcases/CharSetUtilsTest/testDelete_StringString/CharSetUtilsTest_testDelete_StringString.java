package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSetUtils#delete(String, String...)}.
 *
 * <p>{@code delete} removes from the input string every character that belongs
 * to the given set (the set uses {@link CharSet} syntax, e.g. {@code "a-e"} is
 * the range a..e). This test verifies how the method handles null/empty inputs
 * as well as ordinary deletion.</p>
 */
public class CharSetUtilsTest_testDelete_StringString extends AbstractLangTest {

    @Test
    void testDelete_StringString() {
        // A null input string always yields null, regardless of the set.
        assertNull(CharSetUtils.delete(null, (String) null), "null string, null set");
        assertNull(CharSetUtils.delete(null, ""), "null string, empty set");

        // An empty input string stays empty for any set.
        assertEquals("", CharSetUtils.delete("", (String) null), "empty string, null set");
        assertEquals("", CharSetUtils.delete("", ""), "empty string, empty set");
        assertEquals("", CharSetUtils.delete("", "a-e"), "empty string, non-empty set");

        // A null or empty set means there is nothing to delete: the string is unchanged.
        assertEquals("hello", CharSetUtils.delete("hello", (String) null), "null set deletes nothing");
        assertEquals("hello", CharSetUtils.delete("hello", ""), "empty set deletes nothing");

        // Characters matching the set are removed; others are kept in order.
        assertEquals("hllo", CharSetUtils.delete("hello", "a-e"), "delete 'e' (in range a-e)");
        assertEquals("he", CharSetUtils.delete("hello", "l-p"), "delete 'l' and 'o' (in range l-p)");

        // A set that matches no character leaves the string unchanged.
        assertEquals("hello", CharSetUtils.delete("hello", "z"), "set 'z' matches nothing");
    }
}
