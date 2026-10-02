package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSetUtils#squeeze(String, String...)} with a single set argument.
 *
 * <p>{@code squeeze} collapses consecutive repetitions of any character that belongs
 * to the supplied set, leaving characters outside the set untouched.</p>
 */
public class CharSetUtilsTest_testSqueeze_StringString extends AbstractLangTest {

    @Test
    void testSqueeze_StringString() {
        // A null input string always yields null, regardless of the set.
        assertNull(CharSetUtils.squeeze(null, (String) null));
        assertNull(CharSetUtils.squeeze(null, ""));

        // An empty input string is returned unchanged (as empty).
        assertEquals("", CharSetUtils.squeeze("", (String) null));
        assertEquals("", CharSetUtils.squeeze("", ""));
        assertEquals("", CharSetUtils.squeeze("", "a-e"));

        // A null or empty set means there is nothing to squeeze: the string is unchanged.
        assertEquals("hello", CharSetUtils.squeeze("hello", (String) null));
        assertEquals("hello", CharSetUtils.squeeze("hello", ""));

        // Set "a-e" does not include 'l', so the doubled 'l' in "hello" is kept.
        assertEquals("hello", CharSetUtils.squeeze("hello", "a-e"));

        // Set "l-p" includes 'l', so the doubled 'l' is squeezed to a single 'l'.
        assertEquals("helo", CharSetUtils.squeeze("hello", "l-p"));

        // Only 'l' is squeezed; the trailing doubled 'o' stays because 'o' is not in the set.
        assertEquals("heloo", CharSetUtils.squeeze("helloo", "l"));

        // Negated set "^l" matches everything except 'l', so 'o' is squeezed but 'l' is not.
        assertEquals("hello", CharSetUtils.squeeze("helloo", "^l"));
    }
}
