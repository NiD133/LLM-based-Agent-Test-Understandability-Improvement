package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testSqueeze_StringStringarray extends AbstractLangTest {

    @Test
    void testSqueeze_nullStringReturnsNull() {
        // A null input string always returns null, regardless of the character set argument
        assertNull(CharSetUtils.squeeze(null, (String[]) null));
        assertNull(CharSetUtils.squeeze(null));
        assertNull(CharSetUtils.squeeze(null, (String) null));
        assertNull(CharSetUtils.squeeze(null, "el"));
    }

    @Test
    void testSqueeze_emptyStringReturnsEmpty() {
        // An empty input string always returns an empty string, regardless of the character set argument
        assertEquals("", CharSetUtils.squeeze("", (String[]) null));
        assertEquals("", CharSetUtils.squeeze(""));
        assertEquals("", CharSetUtils.squeeze("", (String) null));
        assertEquals("", CharSetUtils.squeeze("", "a-e"));
    }

    @Test
    void testSqueeze_nullOrEmptySetReturnsOriginalString() {
        // A null or empty character set means no characters are eligible for squeezing
        assertEquals("hello", CharSetUtils.squeeze("hello", (String[]) null));
        assertEquals("hello", CharSetUtils.squeeze("hello"));
        assertEquals("hello", CharSetUtils.squeeze("hello", (String) null));
    }

    @Test
    void testSqueeze_noConsecutiveDuplicatesInSetReturnsOriginalString() {
        // Squeezing only collapses characters that are both consecutive AND present in the set
        assertEquals("hello", CharSetUtils.squeeze("hello", "a-e")); // 'll' is consecutive but 'l' not in "a-e"
        assertEquals("hello", CharSetUtils.squeeze("hello", "e"));   // 'e' is in the set but not consecutive
    }

    @Test
    void testSqueeze_consecutiveDuplicatesInSetAreSqueezeToOne() {
        // Each run of consecutive characters that appear in the set is collapsed to a single character
        assertEquals("helo",  CharSetUtils.squeeze("hello",     "el")); // 'll' → 'l' because 'l' is in "el"
        assertEquals("fofof", CharSetUtils.squeeze("fooffooff", "of")); // 'oo' → 'o' and 'ff' → 'f' in "of"
        assertEquals("fof",   CharSetUtils.squeeze("fooooff",   "fo")); // 'oooo' → 'o' and 'ff' → 'f' in "fo"
    }
}
